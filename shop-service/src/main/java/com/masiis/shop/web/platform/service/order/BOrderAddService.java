package com.masiis.shop.web.platform.service.order;

import com.masiis.shop.common.enums.platform.BOrderStatus;
import com.masiis.shop.common.enums.platform.BOrderType;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.OrderMakeUtils;
import com.masiis.shop.dao.beans.order.BOrderAdd;
import com.masiis.shop.dao.platform.order.PfBorderConsigneeMapper;
import com.masiis.shop.dao.platform.order.PfBorderItemMapper;
import com.masiis.shop.dao.platform.order.PfBorderMapper;
import com.masiis.shop.dao.platform.product.PfSkuAgentMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.platform.user.PfUserCertificateMapper;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.common.service.ComAgentLevelService;
import com.masiis.shop.web.common.service.UserAddressService;
import com.masiis.shop.web.common.service.UserService;
import com.masiis.shop.web.platform.service.product.PfUserSkuStockService;
import com.masiis.shop.web.platform.service.product.SkuAgentService;
import com.masiis.shop.web.common.service.SkuService;
import com.masiis.shop.web.platform.service.user.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.swing.border.Border;
import java.math.BigDecimal;
import java.security.interfaces.RSAMultiPrimePrivateCrtKey;
import java.util.Date;
import java.util.List;

/**
 * BOrderAddService
 *
 * @author ZhaoLiang
 * @date 2016/4/14
 */
@Service
public class BOrderAddService {
    private final static Logger logger = Logger.getLogger(BOrderService.class);
    @Resource
    private PfBorderMapper pfBorderMapper;
    @Resource
    private PfBorderItemMapper pfBorderItemMapper;
    @Resource
    private PfBorderConsigneeMapper pfBorderConsigneeMapper;
    @Resource
    private ComUserMapper comUserMapper;
    @Resource
    private BOrderOperationLogService bOrderOperationLogService;
    @Resource
    private PfUserSkuStockService pfUserSkuStockService;
    @Resource
    private PfSkuAgentMapper pfSkuAgentMapper;
    @Resource
    private SkuService skuService;
    @Resource
    private UserAddressService userAddressService;
    @Resource
    private SkuAgentService skuAgentService;
    @Resource
    private PfUserStatisticsService pfUserStatisticsService;
    @Resource
    private PfUserSkuService pfUserSkuService;
    @Resource
    private PfUserRecommendRelationService pfUserRecommendRelationService;
    @Resource
    private PfBorderRecommenRewardService pfBorderRecommenRewardService;
    @Resource
    private PfUserUpgradeNoticeService userUpgradeNoticeService;
    @Resource
    private UserService userService;
    @Resource
    private PfBorderPromotionService pfBorderPromotionService;

    /**
     * 添加订单
     *
     * @param bOrderAdd
     * @return
     * @throws Exception
     */
    @Transactional
    public Long addBOrder(BOrderAdd bOrderAdd) throws Exception {
        ComUser comUser = userService.getUserById(bOrderAdd.getUserId());
        ComSku comSku = skuService.getSkuById(bOrderAdd.getSkuId());
        PfSkuAgent pfSkuAgent = skuAgentService.getBySkuIdAndLevelId(comSku.getId(), bOrderAdd.getAgentLevelId());
        if (comUser == null || comUser.getIsBinding() == 0) {
            throw new BusinessException("用户还未绑定手机号");
        }
        BigDecimal retailPrice = comSku.getPriceRetail();//微信零售价
        BigDecimal unitPrice = pfSkuAgent.getUnitPrice();//代理单价
        Integer quantity;//代理数量
        BigDecimal bailPrice;//代理保证金
        String orderRemark = "";
        //获取保证金和数量
        if (bOrderAdd.getOrderType().equals(BOrderType.agent.getCode())) {
            bailPrice = pfSkuAgent.getBail();
            quantity = pfSkuAgent.getQuantity();
            orderRemark = "新增" + BOrderType.agent.getDesc();
        } else if (bOrderAdd.getOrderType().equals(BOrderType.Supplement.getCode())) {
            bailPrice = BigDecimal.ZERO;
            quantity = bOrderAdd.getQuantity();
            orderRemark = "新增" + BOrderType.Supplement.getDesc();
        } else if (bOrderAdd.getOrderType().equals(BOrderType.UPGRADE.getCode())) {
            PfUserSku pfUserSku = pfUserSkuService.getPfUserSkuByUserIdAndSkuId(comUser.getId(), comSku.getId());
            logger.info("新代理保证金-------" + pfSkuAgent.getBail());
            logger.info("旧代理保证金-------" + pfUserSku.getBail());
            bailPrice = pfSkuAgent.getBail().subtract(pfUserSku.getBail());
            //如果之前缴纳的保证金比现在的保证金标准高，那么不需要再次缴纳保证金
            logger.info("bailPrice-----before---"+bailPrice.toString());
            if (bailPrice.compareTo(BigDecimal.ZERO) < 0) {
                bailPrice = BigDecimal.ZERO;
            }
            logger.info("bailPrice-----after---"+bailPrice.toString());
            quantity = pfSkuAgent.getQuantity();
            orderRemark = "新增" + BOrderType.UPGRADE.getDesc();
        } else {
            throw new BusinessException("订单类型异常");
        }
        BigDecimal productAmount = unitPrice.multiply(BigDecimal.valueOf(quantity));//商品总金额=商品微信销售价*折扣*数量
        //v1.2 Begin 如果合伙人和上级的合伙等级相同，那么合伙人的上级将是推荐人的上级
        Long recommendUserId = 0L;
        PfUserSku _parentPfUserSku = pfUserSkuService.getPfUserSkuByUserIdAndSkuId(bOrderAdd.getpUserId(), bOrderAdd.getSkuId());
        if (bOrderAdd.getpUserId() != 0 && bOrderAdd.getOrderType().equals(BOrderType.agent.getCode())) {
            if (_parentPfUserSku.getAgentLevelId().equals(bOrderAdd.getAgentLevelId())) {
                recommendUserId = bOrderAdd.getpUserId();
                bOrderAdd.setpUserId(_parentPfUserSku.getUserPid());
            }
        }
        //v1.2 End 如果合伙人和上级的合伙等级相同，那么合伙人的上级将是上级的上级
        //v1.4.2 Begin 如果下级合伙人升级，上级合伙人无法升级并且下级合伙人没有推荐人，那么上级合伙人和下级合伙人解除合伙关系，上级合伙人成为下级合伙人的推荐人
        logger.info("获得推荐人-----userId-----"+bOrderAdd.getUserId()+"-------skuId----"+bOrderAdd.getSkuId());
        PfUserRecommenRelation pfUserRecommenRelation = pfUserRecommendRelationService.selectRecommenRelationByUserIdAndSkuId(bOrderAdd.getUserId(), bOrderAdd.getSkuId());
        if (bOrderAdd.getOrderType().equals(BOrderType.UPGRADE.getCode())) {
            if (pfUserRecommenRelation == null || pfUserRecommenRelation.getPid() == 0) {
                List<PfSkuAgent> pfSkuAgents = pfSkuAgentMapper.selectAllBySkuId(bOrderAdd.getSkuId());
                PfUserSku oldPfUserSku = pfUserSkuService.getPfUserSkuByUserIdAndSkuId(bOrderAdd.getOldPUserId(), bOrderAdd.getSkuId());
                logger.info("如果下级合伙人升级，上级合伙人无法升级并且下级合伙人没有推荐人，那么上级合伙人和下级合伙人解除合伙关系，上级合伙人成为下级合伙人的推荐人" + oldPfUserSku.toString());
                Boolean bl = false;
                for (PfSkuAgent pfSkuAgentIn : pfSkuAgents) {
                    if (oldPfUserSku.getAgentLevelId() > pfSkuAgentIn.getAgentLevelId() && pfSkuAgentIn.getIsUpgrade().equals(1)) {
                        bl = true;
                        break;
                    }
                }
                logger.info("bl:" + bl + ";recommendUserId:" + recommendUserId + ";OldPUserId:" + bOrderAdd.getOldPUserId());
                if (bl == false) {
                    recommendUserId = bOrderAdd.getOldPUserId();
                }
            }
        }
        //v1.4.2 End
        logger.info("<1>添加订单表 Border");
        PfBorder pfBorder = addBorder(comUser.getId(),
                bOrderAdd.getUserMessage(),
                bOrderAdd.getUserSource(),
                bOrderAdd.getpUserId(),
                bailPrice,
                bOrderAdd.getShipAmount(),
                productAmount,
                bOrderAdd.getSendType(),
                bOrderAdd.getOrderType(),
                orderRemark);
        logger.info("<2>添加订单商品表 BorderItem");
        PfBorderItem pfBorderItem = addBorderItem(pfBorder.getId(),
                comSku,
                bOrderAdd.getAgentLevelId(),
                quantity,
                retailPrice,
                unitPrice,
                bailPrice);
        //v1.2 Begin处理订单推荐奖励表
        logger.info("<3>添加订单日志 BOrderOperationLog");
        bOrderOperationLogService.insertBOrderOperationLog(pfBorder, "新增订单");
        if (bOrderAdd.getpUserId() != 0) {
            logger.info("<4>处理订单推荐表 BorderRecommenReward");
            PfBorderRecommenReward pfBorderRecommenReward = null;
            if (pfUserRecommenRelation != null && pfUserRecommenRelation.getPid() > 0) {
                pfBorderRecommenReward = new PfBorderRecommenReward();
                pfBorderRecommenReward.setCreateTime(new Date());
                pfBorderRecommenReward.setPfBorderId(pfBorder.getId());
                pfBorderRecommenReward.setPfBorderItemId(pfBorderItem.getId());
                pfBorderRecommenReward.setSkuId(comSku.getId());
                pfBorderRecommenReward.setRecommenUserId(pfUserRecommenRelation.getUserPid());
                pfBorderRecommenReward.setQuantity(pfBorderItem.getQuantity());
                pfBorderRecommenReward.setRewardUnitPrice(pfSkuAgent.getRewardUnitPrice());
                pfBorderRecommenReward.setRewardTotalPrice(pfBorderRecommenReward.getRewardUnitPrice().multiply(BigDecimal.valueOf(pfBorderRecommenReward.getQuantity())));
                pfBorderRecommenReward.setRemark("已经有了推荐关系的奖励");
            } else {
                if (recommendUserId > 0 && !recommendUserId.equals(pfBorder.getUserPid())) {
                    pfBorderRecommenReward = new PfBorderRecommenReward();
                    pfBorderRecommenReward.setCreateTime(new Date());
                    pfBorderRecommenReward.setPfBorderId(pfBorder.getId());
                    pfBorderRecommenReward.setPfBorderItemId(pfBorderItem.getId());
                    pfBorderRecommenReward.setSkuId(pfBorderItem.getSkuId());
                    pfBorderRecommenReward.setRecommenUserId(recommendUserId);
                    pfBorderRecommenReward.setQuantity(pfBorderItem.getQuantity());
                    pfBorderRecommenReward.setRewardUnitPrice(pfSkuAgent.getRewardUnitPrice());
                    pfBorderRecommenReward.setRewardTotalPrice(pfBorderRecommenReward.getRewardUnitPrice().multiply(BigDecimal.valueOf(pfBorderRecommenReward.getQuantity())));
                    pfBorderRecommenReward.setRemark("新建推荐关系的奖励");
                }
            }
            if (pfBorderRecommenReward != null) {
                pfBorderRecommenRewardService.insert(pfBorderRecommenReward);
                pfBorder.setRecommenAmount(pfBorderRecommenReward.getRewardTotalPrice());
                pfBorderMapper.updateById(pfBorder);
            }
        }
        //v1.5.6 小白注册平台赠送商品
        if (pfBorder.getOrderType().equals(BOrderType.agent.getCode())){
            pfBorderPromotionService.registerGiveSkuInitStock(pfBorder.getId(),
                    pfBorder.getUserId(),
                    pfBorder.getUserPid(),
                    pfBorder.getOrderType(),
                    pfBorderItem.getAgentLevelId());
        }
        //v1.2 End处理订单推荐奖励表
        //拿货方式(0未选择1平台代发2自己发货)
        if (pfBorder.getOrderType() == 2 || pfBorder.getSendType() == 2) {
            logger.info("<5>添加订单收货信息表 BorderConsignee");
            addBorderConsignee(bOrderAdd.getUserAddressId(), comUser.getId(), pfBorder.getId());
        }
        if (bOrderAdd.getOrderType().equals(BOrderType.UPGRADE.getCode())) {
            logger.info("<6>修改升级申请单状态 PfUserUpgradeNotice");
            PfUserUpgradeNotice pfUserUpgradeNotice = userUpgradeNoticeService.selectByPrimaryKey(bOrderAdd.getUpgradeNoticeId());
            pfUserUpgradeNotice.setStatus(2);//待支付
            pfUserUpgradeNotice.setPfBorderId(pfBorder.getId());
            userUpgradeNoticeService.update(pfUserUpgradeNotice);
        }
        return pfBorder.getId();
    }

    /**
     * 添加拿货订单
     *
     * @param userId   用户id
     * @param skuId    商品id
     * @param quantity 拿货数量
     * @param message  用户留言
     *                 <1>处理订单数据
     *                 <2>添加订单日志
     *                 <3>冻结sku库存 如果用户id是0 则为平台直接代理商扣减平台商品库存
     *                 <4>添加订单地址信息
     *                 <5>增加统计数据
     * @return
     * @throws Exception
     * @auth:wbj
     */
    @Transactional
    public Long addProductTake(Long userId, Integer skuId, int quantity, String message, long userAddressId) throws Exception {
        logger.info("进入拿货订单处理Service");
        logger.info("<1>处理订单数据");
        PfUserSku pfUserSku = pfUserSkuService.getPfUserSkuByUserIdAndSkuId(userId, skuId);
        if (pfUserSku == null) {
            throw new BusinessException("您还没有代理过此商品，不能拿货。");
        }
        ComUser comUser = comUserMapper.selectByPrimaryKey(userId);
        if (comUser.getSendType() != 1) {
            throw new BusinessException("发货方式不是平台代发，不能拿货");
        }
        Integer levelId = pfUserSku.getAgentLevelId();//代理等级
        Long pUserId = 0L;//上级代理用户id
        BigDecimal amount;//订单总金额
        Long rBOrderId;//返回生成的订单id
        //获取上级代理
        PfUserSku paremtUserSku = pfUserSkuService.getPfUserSkuByUserIdAndSkuId(pfUserSku.getUserPid(), pfUserSku.getSkuId());
        if (paremtUserSku != null) {
            pUserId = paremtUserSku.getUserId();
        }
        PfSkuAgent pfSkuAgent = pfSkuAgentMapper.selectBySkuIdAndLevelId(skuId, levelId);
        ComSku comSku = skuService.getSkuById(skuId);
        amount = pfSkuAgent.getUnitPrice().multiply(BigDecimal.valueOf(quantity));
        //处理订单数据
        PfBorder order = new PfBorder();
        order.setCreateTime(new Date());
        order.setCreateMan(userId);
        String orderCode = OrderMakeUtils.makeOrder("B");
        order.setOrderCode(orderCode);
        order.setUserMessage(message);
        order.setUserId(userId);
        order.setUserPid(pUserId);
        order.setBailAmount(new BigDecimal(0));
        order.setSupplierId(0);
        order.setReceivableAmount(amount);
        order.setOrderAmount(amount);//运费到付，商品总价即订单总金额
        order.setProductAmount(amount);
        order.setShipAmount(BigDecimal.ZERO);
        order.setPayAmount(BigDecimal.ZERO);
        order.setShipType(0);
        order.setSendType(comUser.getSendType());
        order.setOrderStatus(BOrderStatus.WaitShip.getCode());
        order.setShipStatus(0);
        order.setPayStatus(1);//支付
        order.setIsShip(0);
        order.setIsReceipt(0);
        order.setIsCounting(0);
        order.setRemark("拿货订单");
        order.setOrderType(2);
        pfBorderMapper.insert(order);
        rBOrderId = order.getId();
        PfBorderItem pfBorderItem = new PfBorderItem();
        pfBorderItem.setCreateTime(new Date());
        pfBorderItem.setPfBorderId(rBOrderId);
        pfBorderItem.setSpuId(comSku.getSpuId());
        pfBorderItem.setSkuId(comSku.getId());
        pfBorderItem.setSkuName(comSku.getName());
        pfBorderItem.setBailAmount(new BigDecimal(0));
        pfBorderItem.setQuantity(quantity);
        pfBorderItem.setAgentLevelId(levelId);
        pfBorderItem.setOriginalPrice(comSku.getPriceRetail());
        pfBorderItem.setUnitPrice(pfSkuAgent.getUnitPrice());//合伙人价
        pfBorderItem.setTotalPrice(pfSkuAgent.getUnitPrice().multiply(BigDecimal.valueOf(quantity)));
        pfBorderItem.setIsComment(0);
        pfBorderItem.setIsReturn(0);
        pfBorderItemMapper.insert(pfBorderItem);
        logger.info("<2>添加订单日志");
        bOrderOperationLogService.insertBOrderOperationLog(order, "订单已支付,拿货订单");
        logger.info("<3>冻结usersku库存 用户加冻结库存存");
        PfUserSkuStock pfUserSkuStock;
        //冻结usersku库存 用户加冻结库存
        pfUserSkuStock = pfUserSkuStockService.selectByUserIdAndSkuId(userId, pfBorderItem.getSkuId());
        if (pfUserSkuStock == null) {
            throw new BusinessException("拿货失败：没有库存信息");
        }
        if (pfUserSkuStock.getStock() - pfUserSkuStock.getFrozenStock() < quantity) {
            throw new BusinessException("拿货失败：拿货数量超过库存数量");
        }
        pfUserSkuStock.setFrozenStock(pfUserSkuStock.getFrozenStock() + quantity);
        if (pfUserSkuStockService.updateByIdAndVersions(pfUserSkuStock) == 0) {
            throw new BusinessException("并发修改库存失败");
        }
        logger.info("<4>添加订单地址信息");
        ComUserAddress comUserAddress = userAddressService.getUserAddressById(userAddressId);
        PfBorderConsignee pfBorderConsignee = new PfBorderConsignee();
        pfBorderConsignee.setCreateTime(new Date());
        pfBorderConsignee.setPfBorderId(order.getId());
        pfBorderConsignee.setUserId(comUserAddress.getUserId());
        pfBorderConsignee.setConsignee(comUserAddress.getName());
        pfBorderConsignee.setMobile(comUserAddress.getMobile());
        pfBorderConsignee.setProvinceId(comUserAddress.getProvinceId());
        pfBorderConsignee.setProvinceName(comUserAddress.getProvinceName());
        pfBorderConsignee.setCityId(comUserAddress.getCityId());
        pfBorderConsignee.setCityName(comUserAddress.getCityName());
        pfBorderConsignee.setRegionId(comUserAddress.getRegionId());
        pfBorderConsignee.setRegionName(comUserAddress.getRegionName());
        pfBorderConsignee.setAddress(comUserAddress.getAddress());
        pfBorderConsignee.setZip(comUserAddress.getZip());
        pfBorderConsigneeMapper.insert(pfBorderConsignee);
        logger.info("<5>增加统计数据");
        PfUserStatistics pfUserStatistics = pfUserStatisticsService.selectByUserIdAndSkuId(userId, pfBorderItem.getSkuId());
        pfUserStatistics.setTakeOrderCount(pfUserStatistics.getTakeOrderCount() + 1);
        pfUserStatistics.setTakeProductCount(pfUserStatistics.getTakeProductCount() + pfBorderItem.getQuantity());
//        pfUserStatistics.setTakeFee(pfBorderItem.getTotalPrice());
        pfUserStatistics.setVersion(pfUserStatistics.getVersion());
        pfUserStatisticsService.updateByIdAndVersion(pfUserStatistics);
        return rBOrderId;
    }


    /**
     * 添加订单主表
     *
     * @param userId        下单人
     * @param userMessage   下单人留言
     * @param userSource    下单人来源
     * @param userPid       上级合伙人0为平台
     * @param bailAmount    保证金
     * @param shipAmount    运费
     * @param productAmount 商品总金额
     * @param sendType      拿货方式(0未选择1平台代发2自己发货)
     * @param orderType     订单类型(0代理1补货2拿货)
     * @return
     */
    private PfBorder addBorder(Long userId,
                               String userMessage,
                               Integer userSource,
                               Long userPid,
                               BigDecimal bailAmount,
                               BigDecimal shipAmount,
                               BigDecimal productAmount,
                               Integer sendType,
                               Integer orderType,
                               String orderRemark) {
        BigDecimal orderAmount = bailAmount.add(shipAmount).add(productAmount);
        //处理订单数据
        PfBorder pfBorder = new PfBorder();
        pfBorder.setCreateTime(new Date());
        pfBorder.setCreateMan(userId);
        String orderCode = OrderMakeUtils.makeOrder("B");
        pfBorder.setOrderCode(orderCode);
        pfBorder.setUserMessage(userMessage);
        pfBorder.setUserSource(userSource);
        pfBorder.setUserId(userId);
        pfBorder.setUserPid(userPid);
        pfBorder.setSupplierId(0);
        pfBorder.setBailAmount(bailAmount);
        pfBorder.setReceivableAmount(orderAmount);
        pfBorder.setOrderAmount(orderAmount);
        pfBorder.setProductAmount(productAmount);
        pfBorder.setShipAmount(shipAmount);
        pfBorder.setPayAmount(BigDecimal.ZERO);
        pfBorder.setShipManId(0);
        pfBorder.setShipManName("");
        pfBorder.setShipType(0);
        //确定订单的拿货方式
        pfBorder.setSendType(sendType);
        pfBorder.setOrderType(orderType);
        pfBorder.setOrderStatus(0);
        pfBorder.setShipStatus(0);
        pfBorder.setPayStatus(0);
        pfBorder.setIsCounting(0);
        pfBorder.setIsShip(0);
        pfBorder.setIsReceipt(0);
        pfBorder.setRecommenAmount(BigDecimal.ZERO);
        pfBorder.setRemark(orderRemark);
        //添加订单
        pfBorderMapper.insert(pfBorder);
        return pfBorder;
    }

    /**
     * 添加订单商品信息表
     *
     * @param bOrderId      订单id
     * @param comSku        sku对象
     * @param agentLevelId  代理等级
     * @param quantity      数量
     * @param originalPrice 原价
     * @param unitPrice     单价
     * @param bailPrice     保证金
     * @return
     */
    private PfBorderItem addBorderItem(Long bOrderId,
                                       ComSku comSku,
                                       Integer agentLevelId,
                                       Integer quantity,
                                       BigDecimal originalPrice,
                                       BigDecimal unitPrice,
                                       BigDecimal bailPrice) {
        //处理订单商品数据
        PfBorderItem pfBorderItem = new PfBorderItem();
        pfBorderItem.setPfBorderId(bOrderId);
        pfBorderItem.setCreateTime(new Date());
        pfBorderItem.setSpuId(comSku.getSpuId());
        pfBorderItem.setSkuId(comSku.getId());
        pfBorderItem.setSkuName(comSku.getName());
        pfBorderItem.setAgentLevelId(agentLevelId);
        pfBorderItem.setQuantity(quantity);
        pfBorderItem.setOriginalPrice(originalPrice);
        pfBorderItem.setUnitPrice(unitPrice);
        pfBorderItem.setTotalPrice(unitPrice.multiply(BigDecimal.valueOf(quantity)));
        pfBorderItem.setBailAmount(bailPrice);
        pfBorderItem.setIsComment(0);
        pfBorderItem.setIsReturn(0);
        pfBorderItemMapper.insert(pfBorderItem);
        return pfBorderItem;
    }

    /**
     * 添加订单收货人信息表
     *
     * @param userAddressId 用户收货地址id
     * @param userId        用户id
     * @param bOrderId      订单id
     */
    private void addBorderConsignee(Long userAddressId, Long userId, Long bOrderId) {
        //获得地址
        ComUserAddress comUserAddress = userAddressService.getOrderAddress(userAddressId, userId);
        if (comUserAddress == null) {
            throw new BusinessException("请填写收货地址");
        }
        PfBorderConsignee pfBorderConsignee = new PfBorderConsignee();
        pfBorderConsignee.setPfBorderId(bOrderId);
        pfBorderConsignee.setCreateTime(new Date());
        pfBorderConsignee.setUserId(comUserAddress.getUserId());
        pfBorderConsignee.setConsignee(comUserAddress.getName());
        pfBorderConsignee.setMobile(comUserAddress.getMobile());
        pfBorderConsignee.setProvinceId(comUserAddress.getProvinceId());
        pfBorderConsignee.setProvinceName(comUserAddress.getProvinceName());
        pfBorderConsignee.setCityId(comUserAddress.getCityId());
        pfBorderConsignee.setCityName(comUserAddress.getCityName());
        pfBorderConsignee.setRegionId(comUserAddress.getRegionId());
        pfBorderConsignee.setRegionName(comUserAddress.getRegionName());
        pfBorderConsignee.setAddress(comUserAddress.getAddress());
        pfBorderConsignee.setZip(comUserAddress.getZip());
        pfBorderConsigneeMapper.insert(pfBorderConsignee);
    }
}
