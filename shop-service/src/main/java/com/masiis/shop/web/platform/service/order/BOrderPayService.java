package com.masiis.shop.web.platform.service.order;

import com.masiis.shop.common.constant.platform.SysConstants;
import com.masiis.shop.common.enums.platform.BOrderStatus;
import com.masiis.shop.common.enums.promotion.SfTurnTableRuleStatusEnum;
import com.masiis.shop.common.enums.promotion.SfTurnTableRuleTypeEnum;
import com.masiis.shop.common.enums.promotion.SfUserTurnTableTimesTypeEnum;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.*;
import com.masiis.shop.dao.platform.order.PfBorderItemMapper;
import com.masiis.shop.dao.platform.order.PfBorderMapper;
import com.masiis.shop.dao.platform.order.PfBorderPaymentMapper;
import com.masiis.shop.dao.platform.product.ComAgentLevelMapper;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.common.utils.DrawPicUtil;
import com.masiis.shop.web.common.utils.notice.SysNoticeUtils;
import com.masiis.shop.web.platform.service.product.PfSkuStockService;
import com.masiis.shop.web.platform.service.product.PfUserSkuStockService;
import com.masiis.shop.web.platform.service.user.PfUserStatisticsService;
import com.masiis.shop.web.promotion.cpromotion.service.gorder.SfTurnTableRuleService;
import com.masiis.shop.web.promotion.cpromotion.service.guser.SfUserTurnTableService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * 合伙订单支付回调处理类
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
    private ComAgentLevelMapper comAgentLevelMapper;
    @Resource
    private PfSupplierBankService supplierBankService;
    @Resource
    private BOrderOperationLogService bOrderOperationLogService;
    @Resource
    private PfSkuStockService pfSkuStockService;
    @Resource
    private PfUserSkuStockService pfUserSkuStockService;
    @Resource
    private PfUserStatisticsService pfUserStatisticsService;
    @Resource
    private BOrderStatisticsService orderStatisticsService;
    @Resource
    private BOrderBillAmountService billAmountService;
    @Resource
    private BUpgradePayService upgradePayService;
    @Resource
    private BOrderPayEndMessageService bOrderPayEndMessageService;
    @Resource
    private BOrderShipService bOrderShipService;
    @Resource
    private BOrderPayAgentService bOrderPayAgentService;
    @Resource
    private SfTurnTableRuleService turnTableRuleService;
    @Resource
    private SfUserTurnTableService userTurnTableService;

    /**
     * 订单支付回调入口
     *
     * @param pfBorderPayment 订单支付信息表数据
     * @param outOrderId      第三方支付订单号
     * @throws Exception
     */
    @Transactional
    public void mainPayBOrder(PfBorderPayment pfBorderPayment, String outOrderId) throws Exception {
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
        //支付完成推送消息(发送失败不回滚事务)
        try {
            bOrderPayEndMessageService.payEndPushMessage(pfBorderPayment);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
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
        userTurnTableService.addTimes(null, pfBorder.getUserId(), SfTurnTableRuleTypeEnum.B.getCode());
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
     * 获取订单详情
     *
     * @author hanzengzhi
     * @date 2016/5/11 20:33
     */
    public PfBorder getOrderDetail(Long orderId) {
        return pfBorderMapper.selectByPrimaryKey(orderId);
    }

    /**
     * 线下支付
     *
     * @author hanzengzhi
     * @date 2016/4/25 14:46
     */
    public Boolean offinePayment(ComUser comUser, Long bOrderId) throws Exception {
        //修改订单状态
        try {
            log.info("线下支付修改状态--------borderId------" + bOrderId);
            PfBorder pfBorder = updateOrderStatus(BOrderStatus.offLineNoPay.getCode(), bOrderId);
            if (pfBorder != null) {
                //插入订单支付表
                insertOrderPayment(pfBorder);
                bOrderOperationLogService.insertBOrderOperationLog(pfBorder, "订单线下已支付");
                List<PfBorderItem> orderItems = pfBorderItemMapper.selectAllByOrderId(pfBorder.getId());
                if (orderItems != null && orderItems.size() != 0) {
                    //发送微信短信提醒
                    //offinePaymentNotice(comUser, pfBorder, orderItems.get(0), supplierBank);
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    /**
     * 修改订单的状态
     *
     * @author hanzengzhi
     * @date 2016/4/25 14:49
     */
    private PfBorder updateOrderStatus(Integer status, Long bOrderId) throws Exception {
        PfBorder pfBorder = pfBorderMapper.selectByPrimaryKey(bOrderId);
        if (pfBorder != null) {
            Integer payStatus = pfBorder.getPayStatus();
            if (payStatus.equals(1)) {
                throw new BusinessException("该订单已支付,无需再支付");
            }
            pfBorder.setOrderStatus(status);
            int i = pfBorderMapper.updateById(pfBorder);
            if (i != 1) {
                throw new BusinessException("线下支付更新订单状态失败");
            }
        } else {
            throw new BusinessException("线下支付失败:查询订单信息失败");
        }
        return pfBorder;
    }

    /**
     * 线下支付微信短信通知
     *
     * @author hanzengzhi
     * @date 2016/5/5 10:56
     */
    private void offinePaymentNotice(ComUser comUser, PfBorder border, PfBorderItem orderItem, PfSupplierBank supplierBank) {
        //微信通知
        StringBuffer sb = new StringBuffer();
        log.info("用户id----" + comUser.getId() + "-------skuId----" + orderItem.getSkuId());
        String agentLevelName = null;
        if (orderItem != null) {
            log.info("合伙人等级id--------" + orderItem.getAgentLevelId());
            ComAgentLevel comAgentLevel = comAgentLevelMapper.selectByPrimaryKey(orderItem.getAgentLevelId());
            if (comAgentLevel != null) {
                agentLevelName = comAgentLevel.getName();
                log.info("合伙人等级名字--------" + agentLevelName);
            }
        }
        sb.append(orderItem.getSkuName());
        if (!StringUtils.isEmpty(agentLevelName)) {
            sb.append(agentLevelName);
        }
        sb.append("订单");
        String[] param = new String[]{border.getOrderCode(), sb.toString()};
        String offinePaymentUrl = PropertiesUtils.getStringValue("web.domain.name.address") + "/borderManage/borderDetils.html?id=" + border.getId();
        SysNoticeUtils.getInstance().offLinePayNotice(comUser, param, border.getCreateTime(), offinePaymentUrl);
        //短信通知
        StringBuffer sb2 = new StringBuffer();
        if (supplierBank != null) {
            sb2.append("开户行:").append(supplierBank.getBankName());
            sb2.append(" 开户名:").append(supplierBank.getAccountName());
            sb2.append(" 卡号:").append(supplierBank.getCardNumber());
            sb2.append(" 账号上");
        }
        log.info("银行卡信息-------" + sb2.toString());
        //最迟日期
        Boolean bl = MobileMessageUtil.getInitialization("B").offlinePaymentsRemind(comUser.getMobile(), border.getOrderCode(), border.getReceivableAmount(), DateUtil.insertDay(border.getCreateTime()), sb2.toString());
        if (bl) {
            log.info("发送短信成功");
        } else {
            log.info("发送短信失败");
        }
    }

    /**
     * 插入订单支付表
     *
     * @author hanzengzhi
     * @date 2016/4/27 14:19
     */
    private void insertOrderPayment(PfBorder pfBorder) {
        PfBorderPayment orderPayment = new PfBorderPayment();
        orderPayment.setPayTypeId(1);
        orderPayment.setPayTypeName("线下支付");
        orderPayment.setPfBorderId(pfBorder.getId());
        orderPayment.setIsEnabled(0);
        orderPayment.setAmount(pfBorder.getOrderAmount());
        orderPayment.setCreateTime(new Date());
        orderPayment.setOutOrderId("");
        orderPayment.setPaySerialNum(UUID.randomUUID().toString());
        orderPayment.setRemark("线下支付插入");
        PfBorderPayment _orderPayment = pfBorderPaymentMapper.selectByOrderIdAndPayTypeIdAndIsEnabled(pfBorder.getId(), 1, 0);
        if (_orderPayment == null) {
            log.info("线下支付:订单支付表中没有输入，插入数据----start");
            int i = pfBorderPaymentMapper.insert(orderPayment);
            if (i != 1) {
                throw new BusinessException("线下支付往订单支付表插入失败");
            }
            log.info("线下支付:订单支付表中没有输入，插入数据----end");
        }
    }

    /**
     * 线下支付
     *
     * @author hanzengzhi
     * @date 2016/4/25 14:46
     */
    public Map<String, Object> getOffinePaymentDeatil(Long bOrderId) throws BusinessException {
        log.info("获取线下支付详情------start");
        log.info("borderId--------------" + bOrderId);
        Map<String, Object> map = null;
        PfBorder pfBorder = pfBorderMapper.selectByPrimaryKey(bOrderId);
        if (pfBorder != null) {
            PfSupplierBank supplierBank = getDefaultBack();
            List<PfBorderItem> orderItems = pfBorderItemMapper.selectAllByOrderId(pfBorder.getId());
            if (orderItems != null && orderItems.size() != 0) {
                map = new LinkedHashMap<String, Object>();
                map.put("latestTime", DateUtil.addDays(SysConstants.OFFINE_PAYMENT_LATEST_TIME));
                map.put("supplierBank", supplierBank);
                map.put("orderItem", orderItems.get(0));
                map.put("border", pfBorder);
            } else {
                throw new BusinessException("获取订单信息失败");
            }
        }
        log.info("获取线下支付详情------end");
        return map;
    }


    /**
     * 获得运营商的默认银行卡信息
     *
     * @author hanzengzhi
     * @date 2016/4/25 14:50
     */
    private PfSupplierBank getDefaultBack() {
        return supplierBankService.getDefaultBank();
    }
}
