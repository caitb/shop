package com.masiis.shop.admin.service.order;

import com.masiis.shop.admin.service.product.PfSkuStockService;
import com.masiis.shop.admin.service.product.PfUserSkuStockService;
import com.masiis.shop.admin.service.product.SkuService;
import com.masiis.shop.admin.service.promotion.SfUserTurnTableService;
import com.masiis.shop.admin.service.shop.SfShopStatisticsService;
import com.masiis.shop.admin.service.system.PbOperationLogService;
import com.masiis.shop.admin.service.user.ComUserAccountService;
import com.masiis.shop.admin.service.user.ComUserService;
import com.masiis.shop.admin.service.user.PfUserRecommendRelationService;
import com.masiis.shop.admin.service.user.PfUserStatisticsService;
import com.masiis.shop.admin.utils.DrawPicUtil;
import com.masiis.shop.common.constant.platform.SysConstants;
import com.masiis.shop.common.enums.platform.BOrderShipStatus;
import com.masiis.shop.common.enums.platform.BOrderStatus;
import com.masiis.shop.common.enums.platform.OperationType;
import com.masiis.shop.common.enums.platform.SkuStockLogType;
import com.masiis.shop.common.enums.platform.UserSkuStockLogType;
import com.masiis.shop.common.enums.promotion.SfTurnTableRuleTypeEnum;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.platform.order.PfBorderItemMapper;
import com.masiis.shop.dao.platform.order.PfBorderMapper;
import com.masiis.shop.dao.platform.order.PfBorderPaymentMapper;
import com.masiis.shop.dao.po.*;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * payBOrderService
 *
 * @author ZhaoLiang
 * @date 2016/3/30111
 */
@Service
@Transactional
public class BOrderPayService {

    private Logger log = Logger.getLogger(this.getClass());
    @Resource
    private PfBorderMapper pfBorderMapper;
    @Resource
    private PfBorderItemMapper pfBorderItemMapper;
    @Resource
    private PfBorderPaymentMapper pfBorderPaymentMapper;
    @Resource
    private BOrderOperationLogService bOrderOperationLogService;
    @Resource
    private PfSkuStockService pfSkuStockService;
    @Resource
    private PfUserSkuStockService pfUserSkuStockService;
    @Resource
    private BOrderStatisticsService orderStatisticsService;
    @Resource
    private BOrderBillAmountService billAmountService;
    @Resource
    private BUpgradePayService upgradePayService;
    @Resource
    private BOrderPayEndMessageService bOrderPayEndMessageService;
    @Resource
    private BOrderPayAgentService bOrderPayAgentService;
    @Resource
    private PbOperationLogService pbOperationLogService;
    @Resource
    private PfBorderRecommenRewardService pfBorderRecommenRewardService;
    @Resource
    private PfUserStatisticsService pfUserStatisticsService;
    @Resource
    private ComUserAccountService comUserAccountService;
    @Resource
    private BOrderShipService bOrderShipService;
    @Resource
    private SfUserTurnTableService userTurnTableService;
    @Resource
    private ComUserService comUserService;

    public void payBOrderOffline(PfBorderPayment pfBorderPayment, String outOrderId, BigDecimal payAmount, String rootPath, PbUser pbUser) throws Exception {
        if (pfBorderPayment == null) {
            throw new BusinessException("pfBorderPayment为空");
        }
        if (pfBorderPayment.getIsEnabled() == 1) {
            throw new BusinessException("该支付记录已经被处理成功");
        }
        if (payAmount.compareTo(pfBorderPayment.getAmount()) == 0) {
            mainPayBOrder(pfBorderPayment, outOrderId, pbUser);
        } else {
            PfBorder pfBorder = pfBorderMapper.selectByPrimaryKey(pfBorderPayment.getPfBorderId());
            List<PfBorderItem> pfBorderItems = pfBorderItemMapper.selectAllByOrderId(pfBorder.getId());
            if (pfBorderItems.size() != 1) {
                throw new BusinessException("代理商品表有误");
            }
            BigDecimal productAmount = BigDecimal.ZERO;
            BigDecimal recommenAmount = BigDecimal.ZERO;
            for (PfBorderItem pfBorderItem : pfBorderItemMapper.selectAllByOrderId(pfBorder.getId())) {
                int quantity = payAmount.divide(pfBorderItem.getUnitPrice(), 0, BigDecimal.ROUND_DOWN).intValue();
                pfBorderItem.setQuantity(quantity);
                pfBorderItem.setTotalPrice(pfBorderItem.getUnitPrice().multiply(BigDecimal.valueOf(pfBorderItem.getQuantity())));
                pfBorderItem.setBailAmount(BigDecimal.ZERO);
                if (pfBorderItemMapper.updateById(pfBorderItem) != 1) {
                    throw new BusinessException("代理订单商品表操作失败id:" + pfBorderItem.getId());
                }
                productAmount = productAmount.add(pfBorderItem.getTotalPrice());
                PfBorderRecommenReward pfBorderRecommenReward = pfBorderRecommenRewardService.getByPfBorderItemId(pfBorderItem.getId());
                if (pfBorderRecommenReward != null) {
                    pfBorderRecommenReward.setQuantity(pfBorderItem.getQuantity());
                    pfBorderRecommenReward.setRewardTotalPrice(pfBorderRecommenReward.getRewardUnitPrice().multiply(BigDecimal.valueOf(pfBorderItem.getQuantity())));
                    if (pfBorderRecommenRewardService.update(pfBorderRecommenReward) != 1) {
                        throw new BusinessException("代理订单推荐奖励表操作失败id:" + pfBorderRecommenReward.getId());
                    }
                    recommenAmount = recommenAmount.add(pfBorderRecommenReward.getRewardTotalPrice());
                }
            }
            pfBorder.setReceivableAmount(productAmount);
            pfBorder.setOrderAmount(productAmount);
            pfBorder.setBailAmount(BigDecimal.ZERO);
            pfBorder.setShipAmount(BigDecimal.ZERO);
            pfBorder.setRecommenAmount(recommenAmount);
            pfBorder.setProductAmount(productAmount);
            if (payAmount.compareTo(productAmount) > 0) {
                pfBorder.setRemark(pfBorder.getRemark() + "；超出支付金额为:" + payAmount.subtract(productAmount));
            }

            if (pfBorderMapper.updateById(pfBorder) != 1) {
                throw new BusinessException("代理订单表操作失败id:" + pfBorder.getId());
            }
            //添加订单日志
            bOrderOperationLogService.insertBOrderOperationLog(pfBorder, "订单线下部分支付代理");
            PfBorderPayment orderPayment = new PfBorderPayment();
            orderPayment.setPayTypeId(1);
            orderPayment.setPayTypeName("线下支付");
            orderPayment.setPfBorderId(pfBorder.getId());
            orderPayment.setIsEnabled(0);
            orderPayment.setAmount(pfBorder.getOrderAmount());
            orderPayment.setCreateTime(new Date());
            orderPayment.setOutOrderId("");
            orderPayment.setPaySerialNum(UUID.randomUUID().toString());
            orderPayment.setRemark("订单线下部分支付代理插入");
            if (pfBorderPaymentMapper.insert(orderPayment) != 1) {
                throw new BusinessException("线下支付往订单支付表插入失败");
            }
            mainPayBOrder(orderPayment, outOrderId, pbUser);
        }
    }

    /**
     * 订单支付回调入口
     *
     * @param pfBorderPayment 订单支付信息表数据
     * @param outOrderId      第三方支付订单号
     * @throws Exception
     */
    public void mainPayBOrder(PfBorderPayment pfBorderPayment, String outOrderId, PbUser pbUser) throws Exception {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd h:m:s");
//        System.out.println(dateFormat.format(new Date()));
        if (pfBorderPayment == null) {
            throw new BusinessException("pfBorderPayment为空");
        }
        if (pfBorderPayment.getIsEnabled() == 1) {
            throw new BusinessException("该支付记录已经被处理成功");
        }
        PfBorder pfBorder = pfBorderMapper.selectByPrimaryKey(pfBorderPayment.getPfBorderId());
        if (!pfBorderPayment.getAmount().equals(pfBorder.getReceivableAmount())) {
            throw new BusinessException("订单支付金额异常，应收金额：" + pfBorder.getReceivableAmount() + ",支付金额：" + pfBorderPayment.getAmount());
        }
        //处理支付逻辑 订单类型(0代理1补货2拿货)
        if (pfBorder.getOrderType() == 0) {
            bOrderPayAgentService.payBOrderAgent(pfBorderPayment, outOrderId);
        } else if (pfBorder.getOrderType() == 1) {
            payBOrderTypeII(pfBorderPayment, outOrderId);
        } else if (pfBorder.getOrderType() == 2) {
            payBOrderTypeIII(pfBorderPayment, outOrderId);
        } else if (pfBorder.getOrderType() == 3) {
            upgradePayService.paySuccessCallBack(pfBorderPayment, outOrderId);
        } else {
            throw new BusinessException("订单类型有误");
        }
        PbOperationLog pbOperationLog = new PbOperationLog();
        pbOperationLog.setOperateIp(InetAddress.getLocalHost().getHostAddress());
        pbOperationLog.setCreateTime(new Date());
        pbOperationLog.setPbUserId(pbUser.getId());
        pbOperationLog.setPbUserName(pbUser.getUserName());
        pbOperationLog.setOperateType(OperationType.Update.getCode());
        pbOperationLog.setRemark("订单线下支付处理");
        pbOperationLog.setOperateContent(pfBorderPayment.toString());
        int updateByPrimaryKey = pbOperationLogService.insert(pbOperationLog);
        if (updateByPrimaryKey == 0) {
            throw new Exception("日志新建订单支付回调失败!");
        }
        //支付完成推送消息(发送失败不回滚事务)
        try {
            bOrderPayEndMessageService.payEndPushMessage(pfBorderPayment);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
//        System.out.println(dateFormat.format(new Date()));
    }

    /**
     * 自己拿货支付订单
     * <1>修改订单支付信息
     * <2>修改订单数据
     * <3>添加订单日志
     * <4>处理发货库存
     * <5>实时统计数据显示
     * <6>修改结算中数据
     */
    private void payBOrderTypeII(PfBorderPayment pfBorderPayment, String outOrderId) {
        log.info("<1>修改订单支付信息");
        pfBorderPayment.setOutOrderId(outOrderId);
        pfBorderPayment.setIsEnabled(1);//设置为有效
        pfBorderPaymentMapper.updateById(pfBorderPayment);
        BigDecimal payAmount = pfBorderPayment.getAmount();
        Long bOrderId = pfBorderPayment.getPfBorderId();
        log.info("<2>修改订单数据");
        PfBorder pfBorder = pfBorderMapper.selectByPrimaryKey(bOrderId);
        if (pfBorder.getPayStatus() != BOrderStatus.NotPaid.getCode() && pfBorder.getPayStatus() != BOrderStatus.offLineNoPay.getCode()) {
            throw new BusinessException("订单号:" + pfBorder.getId() + ",已经支付成功.");
        }
        pfBorder.setReceivableAmount(pfBorder.getReceivableAmount().subtract(payAmount));
        pfBorder.setPayAmount(pfBorder.getPayAmount().add(payAmount));
        pfBorder.setPayTime(new Date());
        pfBorder.setPayStatus(1);
        pfBorder.setOrderStatus(BOrderStatus.WaitShip.getCode());//待发货
        pfBorderMapper.updateById(pfBorder);
        log.info("<3>添加订单日志");
        bOrderOperationLogService.insertBOrderOperationLog(pfBorder, "");
        for (PfBorderItem pfBorderItem : pfBorderItemMapper.selectAllByOrderId(bOrderId)) {
            log.info("<4>处理发货库存");
            if (pfBorderItem.getQuantity() > 0) {
                if (pfBorder.getUserPid() == 0) {
                    PfSkuStock pfSkuStock = pfSkuStockService.selectBySkuId(pfBorderItem.getSkuId());
                    //如果可售库存不足或者排单开关打开的情况下 订单进入排单
                    if (pfSkuStock.getIsQueue() == 1 || pfSkuStock.getStock() - pfSkuStock.getFrozenStock() < pfBorderItem.getQuantity()) {
                        //平台库存不足，排单处理
                        pfBorder.setOrderStatus(BOrderStatus.MPS.getCode());//排队订单
                        pfBorderMapper.updateById(pfBorder);
                    }
                    //增加平台冻结库存
                    pfSkuStock.setFrozenStock(pfSkuStock.getFrozenStock() + pfBorderItem.getQuantity());
                    if (pfSkuStockService.updateByIdAndVersions(pfSkuStock) != 1) {
                        throw new BusinessException("(平台发货)排队订单增加冻结量失败");
                    }
                } else {
                    PfUserSkuStock parentSkuStock = pfUserSkuStockService.selectByUserIdAndSkuId(pfBorder.getUserPid(), pfBorderItem.getSkuId());
                    //上级合伙人库存不足，排单处理
                    if (pfBorder.getSendType() == 1 && (parentSkuStock.getStock() - parentSkuStock.getFrozenStock() < pfBorderItem.getQuantity())) {
                        pfBorder.setOrderStatus(BOrderStatus.MPS.getCode());//排队订单
                        pfBorderMapper.updateById(pfBorder);
                    }
                    //增加平台冻结库存
                    parentSkuStock.setFrozenStock(parentSkuStock.getFrozenStock() + pfBorderItem.getQuantity());
                    if (pfUserSkuStockService.updateByIdAndVersions(parentSkuStock) != 1) {
                        throw new BusinessException("(代理发货)排队订单增加冻结量失败");
                    }
                }
            }
        }
        log.info("<5>实时统计数据显示");
        orderStatisticsService.statisticsOrder(pfBorder.getId());
        log.info("<6>修改结算中数据");
        billAmountService.orderBillAmount(pfBorder.getId());
        //增加抽奖的次数
        log.info("增加抽奖的次数----start");
        ComUser comUser = comUserService.getUserById(pfBorder.getUserId());
        userTurnTableService.addTimes(comUser, null, SfTurnTableRuleTypeEnum.B.getCode());
        log.info("增加抽奖的次数----end");
        //拿货方式(0未选择1平台代发2自己发货)
        if (pfBorder.getSendType() == 1 && pfBorder.getOrderStatus() == BOrderStatus.WaitShip.getCode()) {
            //处理平台发货类型订单
            bOrderShipService.shipAndReceiptBOrder(pfBorder);
        }
    }

    /**
     * @Author jjh
     * @Date 2016/6/6 0006 下午 4:37
     * 自己提货的订单回调处理
     * <1>修改订单支付信息
     * <2>修改订单数据
     * <3>添加订单日志
     * <4>处理发货库存
     * <5>增加统计数据
     */
    public void payBOrderTypeIII(PfBorderPayment pfBorderPayment, String outOrderId) {
        log.info("<1>修改订单支付信息");
        pfBorderPayment.setOutOrderId(outOrderId);
        pfBorderPayment.setIsEnabled(1);//设置为有效
        pfBorderPaymentMapper.updateById(pfBorderPayment);
        BigDecimal payAmount = pfBorderPayment.getAmount();
        Long bOrderId = pfBorderPayment.getPfBorderId();
        log.info("<2>修改订单数据");
        PfBorder pfBorder = pfBorderMapper.selectByPrimaryKey(bOrderId);
        if (pfBorder.getPayStatus() != BOrderStatus.NotPaid.getCode() && pfBorder.getPayStatus() != BOrderStatus.offLineNoPay.getCode()) {
            throw new BusinessException("订单号:" + pfBorder.getId() + ",已经支付成功.");
        }
        pfBorder.setReceivableAmount(pfBorder.getReceivableAmount().subtract(payAmount));
        pfBorder.setPayAmount(pfBorder.getPayAmount().add(payAmount));
        pfBorder.setPayTime(new Date());
        pfBorder.setPayStatus(1);
        pfBorder.setOrderStatus(BOrderStatus.WaitShip.getCode());//待发货
        pfBorderMapper.updateById(pfBorder);
        log.info("<3>添加订单日志");
        bOrderOperationLogService.insertBOrderOperationLog(pfBorder, "订单已支付,拿货订单");
        for (PfBorderItem pfBorderItem : pfBorderItemMapper.selectAllByOrderId(bOrderId)) {
            log.info("<4>处理发货库存");
            //冻结usersku库存 用户加冻结库存
            PfUserSkuStock pfUserSkuStock = null;
            pfUserSkuStock = pfUserSkuStockService.selectByUserIdAndSkuId(pfBorder.getUserId(), pfBorderItem.getSkuId());
            if (pfUserSkuStock == null) {
                throw new BusinessException("拿货失败：没有库存信息");
            }
            if (pfUserSkuStock.getStock() - pfUserSkuStock.getFrozenStock() < pfBorderItem.getQuantity()) {
                throw new BusinessException("拿货失败：拿货数量超过库存数量");
            }
            pfUserSkuStock.setFrozenStock(pfUserSkuStock.getFrozenStock() + pfBorderItem.getQuantity());
            if (pfUserSkuStockService.updateByIdAndVersions(pfUserSkuStock) == 0) {
                throw new BusinessException("并发修改库存失败");
            }
            log.info("<5>增加统计数据");
            PfUserStatistics pfUserStatistics = pfUserStatisticsService.selectByUserIdAndSkuId(pfBorder.getUserId(), pfBorderItem.getSkuId());
            pfUserStatistics.setTakeOrderCount(pfUserStatistics.getTakeOrderCount() + 1);
            pfUserStatistics.setTakeProductCount(pfUserStatistics.getTakeProductCount() + pfBorderItem.getQuantity());
            pfUserStatistics.setTakeFee(pfBorderItem.getTotalPrice());
            pfUserStatistics.setVersion(pfUserStatistics.getVersion());
            pfUserStatisticsService.updateByIdAndVersion(pfUserStatistics);
        }

    }

    /**
     * 处理平台拿货类型订单
     *
     * @author ZhaoLiang
     * @date 2016/3/30 14:33
     * 操作详情：
     * <1>减少发货方库存 如果用户id是0操作平台库存
     * <2>增加收货方库存
     * <3>订单完成处理
     */
    public void saveBOrderSendType(PfBorder pfBorder) {
        for (PfBorderItem pfBorderItem : pfBorderItemMapper.selectAllByOrderId(pfBorder.getId())) {
            if (pfBorderItem.getQuantity() > 0) {
                log.info("<1>减少发货方库存和冻结库存 如果用户id是0操作平台库存");
                if (pfBorder.getUserPid() == 0) {
                    PfSkuStock pfSkuStock = pfSkuStockService.selectBySkuId(pfBorderItem.getSkuId());
                    if (pfSkuStock.getStock() < pfBorderItem.getQuantity()) {
                        throw new BusinessException("库存不足，操作失败");
                    }
                    if (pfSkuStock.getFrozenStock() < pfBorderItem.getQuantity()) {
                        throw new BusinessException("库存冻结不足，操作失败");
                    }
                    pfSkuStockService.updateSkuStockWithLog(pfBorderItem.getQuantity(), pfSkuStock, pfBorder.getId(), SkuStockLogType.downAgent);
                } else {
                    PfUserSkuStock parentSkuStock = pfUserSkuStockService.selectByUserIdAndSkuId(pfBorder.getUserPid(), pfBorderItem.getSkuId());
                    if (parentSkuStock.getStock() < pfBorderItem.getQuantity()) {
                        throw new BusinessException("库存不足，操作失败");
                    }
                    if (parentSkuStock.getFrozenStock() < pfBorderItem.getQuantity()) {
                        throw new BusinessException("库存冻结不足，操作失败");
                    }
                    pfUserSkuStockService.updateUserSkuStockWithLog(pfBorderItem.getQuantity(), parentSkuStock, pfBorder.getId(), UserSkuStockLogType.downAgent);
                }
                log.info("<2>增加收货方库存");
                PfUserSkuStock pfUserSkuStock = pfUserSkuStockService.selectByUserIdAndSkuId(pfBorder.getUserId(), pfBorderItem.getSkuId());
                pfUserSkuStockService.updateUserSkuStockWithLog(pfBorderItem.getQuantity(), pfUserSkuStock, pfBorder.getId(), UserSkuStockLogType.agent);
            }
        }
        log.info("<3>订单完成处理");
        pfBorder.setShipStatus(BOrderShipStatus.Receipt.getCode());
        pfBorder.setShipTime(new Date());
        pfBorder.setIsShip(1);
        completeBOrder(pfBorder);
    }

    /**
     * 订单完成处理统一入口
     *
     * @author ZhaoLiang
     * @date 2016/4/9 11:22
     */
    public void completeBOrder(PfBorder pfBorder) {
        if (pfBorder == null) {
            throw new BusinessException("订单为空对象");
        }
        if (pfBorder.getPayStatus() != 1) {
            throw new BusinessException("订单还未支付怎么能完成呢？");
        }
        //拿货方式(0未选择1平台代发2自己发货)
        if (pfBorder.getSendType() == 1) {
            if (!pfBorder.getOrderStatus().equals(BOrderStatus.WaitShip.getCode()) && !pfBorder.getOrderStatus().equals(BOrderStatus.MPS.getCode())) {
                throw new BusinessException("订单状态异常:" + pfBorder.getOrderStatus() + ",应是" + BOrderStatus.WaitShip.getCode());
            }
        } else if (pfBorder.getSendType() == 2) {
            if (!pfBorder.getOrderStatus().equals(BOrderStatus.Ship.getCode())) {
                throw new BusinessException("订单状态异常:" + pfBorder.getOrderStatus() + ",应是" + BOrderStatus.Ship.getCode());
            }
        } else {
            throw new BusinessException("订单拿货方式异常");
        }
        pfBorder.setOrderStatus(BOrderStatus.Complete.getCode());//订单完成
        pfBorder.setShipStatus(BOrderShipStatus.Receipt.getCode());//已收货
        pfBorder.setIsReceipt(1);
        pfBorder.setReceiptTime(new Date());//收货时间
        pfBorderMapper.updateById(pfBorder);
        //添加订单日志
        bOrderOperationLogService.insertBOrderOperationLog(pfBorder, "订单完成");
        //订单类型(0代理1补货2拿货)
        if (pfBorder.getOrderType() == 0 || pfBorder.getOrderType() == 1 || pfBorder.getOrderType() == 3) {
            comUserAccountService.countingByOrder(pfBorder);
        }
    }

}
