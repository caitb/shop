package com.masiis.shop.web.platform.service.order;

import com.masiis.shop.common.enums.BOrder.BOrderShipStatus;
import com.masiis.shop.common.enums.BOrder.BOrderStatus;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.common.util.MobileMessageUtil;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.platform.order.*;
import com.masiis.shop.dao.platform.product.ComAgentLevelMapper;
import com.masiis.shop.dao.platform.product.PfSkuAgentMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuMapper;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.platform.constants.SysConstants;
import com.masiis.shop.web.platform.service.product.SkuService;
import com.masiis.shop.web.platform.service.user.ComUserAccountService;
import com.masiis.shop.web.platform.service.user.PfUserStatisticsService;
import com.masiis.shop.web.platform.utils.wx.WxPFNoticeUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by ZhaoLiang on 2016/3/2.
 */
@Service
public class BOrderService {
    private final static Logger logger = Logger.getLogger(BOrderService.class);
    @Resource
    private PfBorderMapper pfBorderMapper;
    @Resource
    private PfBorderItemMapper pfBorderItemMapper;
    @Resource
    private PfBorderConsigneeMapper pfBorderConsigneeMapper;
    @Resource
    private PfBorderPaymentMapper pfBorderPaymentMapper;
    @Resource
    private PfUserSkuMapper pfUserSkuMapper;
    @Resource
    private ComUserMapper comUserMapper;
    @Resource
    private BOrderOperationLogService bOrderOperationLogService;
    @Resource
    private ComAgentLevelMapper comAgentLevelMapper;
    @Resource
    private PfBorderFreightMapper pfBorderFreightMapper;
    @Resource
    private SkuService skuService;
    @Resource
    private BOrderSkuStockService borderSkuStockService;
    @Resource
    private ComUserAccountService comUserAccountService;
    @Resource
    private PfUserStatisticsService userStatisticsService;
    @Resource
    private PfSkuAgentMapper pfSkuAgentMapper;
    @Resource
    private SfShopStatisticsService shopStatisticsService;

    /**
     * 获取订单
     *
     * @author ZhaoLiang
     * @date 2016/3/9 11:07
     */
    public PfBorder getPfBorderById(Long id) {
        return pfBorderMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据订单号获取订单商品
     *
     * @author ZhaoLiang
     * @date 2016/3/9 11:45
     */
    public List<PfBorderItem> getPfBorderItemByOrderId(Long pfBorderId) {
        return pfBorderItemMapper.selectAllByOrderId(pfBorderId);
    }

    /**
     * 根据pfBorderId查询
     *
     * @param pfBorderId
     * @return
     */
    public List<PfBorderItem> getPfBorderItemDetail(Long pfBorderId) {
        return pfBorderItemMapper.getPfBorderItemDetail(pfBorderId);
    }

    /**
     * 获取合伙人等级
     *
     * @author muchaofeng
     * @date 2016/3/9 18:52
     */
    public ComAgentLevel findComAgentLevel(Integer id) {
        return comAgentLevelMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据订单号获取订单
     *
     * @author muchaofeng
     * @date 2016/3/14 13:23
     */
    public PfBorder findByOrderCode(String orderId) {
        return pfBorderMapper.selectByOrderCode(orderId);
    }

    /**
     * 根据用户id获取订单
     *
     * @author muchaofeng
     * @date 2016/3/14 13:22
     */

    public List<PfBorder> findByUserId(Long UserId, Integer orderStatus, Integer sendType) {
        return pfBorderMapper.selectByUserId(UserId, orderStatus, sendType);
    }

    /**
     * 根据用户id获取出货订单
     *
     * @author muchaofeng
     * @date 2016/3/23 14:36
     */
    public List<PfBorder> findByUserPid(Long UserId, Integer orderStatus, Integer sendType) {
        return pfBorderMapper.selectByUserPid(UserId, orderStatus, sendType);
    }

    /**
     * 添加订单支付记录
     *
     * @author ZhaoLiang
     * @date 2016/3/16 12:42
     */
    @Transactional
    public void addBOrderPayment(PfBorderPayment pfBorderPayment) throws Exception {
        pfBorderPaymentMapper.insert(pfBorderPayment);
    }

    /**
     * 根据支付流水号查询支付记录
     *
     * @param paySerialNum
     * @return
     */
    public PfBorderPayment findOrderPaymentBySerialNum(String paySerialNum) {
        return pfBorderPaymentMapper.selectBySerialNum(paySerialNum);
    }

    /**
     * 根据订单号获取快递信息
     *
     * @author muchaofeng
     * @date 2016/3/16 15:21
     */
    public List<PfBorderFreight> findByPfBorderFreightOrderId(Long id) {
        return pfBorderFreightMapper.selectByBorderId(id);
    }

    /**
     * 根据订单号获取收货人信息
     *
     * @author muchaofeng
     * @date 2016/3/16 15:36
     */
    public PfBorderConsignee findpfBorderConsignee(Long id) {
        return pfBorderConsigneeMapper.selectByBorderId(id);
    }

    /**
     * 根据userId获取关系
     *
     * @author muchaofeng
     * @date 2016/3/21 17:37
     */
    public PfUserSku findPfUserSku(long userId, Integer skuId) {
        return pfUserSkuMapper.selectByUserIdAndSkuId(userId, skuId);
    }

    /**
     * 异步查询进货订单
     *
     * @author muchaofeng
     * @date 2016/4/6 14:36
     */
    public List<PfBorder> findPfBorder(long userId, Integer orderStatus, Integer sendType) {
        List<PfBorder> pfBorders = pfBorderMapper.selectByUserId(userId, orderStatus, sendType);
        String skuValue = PropertiesUtils.getStringValue(SysConstants.INDEX_PRODUCT_IMAGE_MIN);
        for (PfBorder pfBorder : pfBorders) {
            List<PfBorderItem> pfBorderItems = pfBorderItemMapper.selectAllByOrderId(pfBorder.getId());
            for (PfBorderItem pfBorderItem : pfBorderItems) {
                pfBorderItem.setSkuUrl(skuValue + skuService.findComSkuImage(pfBorderItem.getSkuId()).getImgUrl());
                pfBorder.setTotalQuantity(pfBorder.getTotalQuantity() + pfBorderItem.getQuantity());//订单商品总量
                pfBorderItem.setSkuMoney(pfBorderItem.getUnitPrice().toString());
            }
            pfBorder.setOrderMoney(pfBorder.getOrderAmount().toString());
            pfBorder.setBailMoney(pfBorder.getBailAmount().toString());
//            if (pfBorder.getUserPid() == 0) {
//                pfBorder.setPidUserName("平台代理");
//            } else {
//                ComUser user = comUserMapper.selectByPrimaryKey(pfBorder.getUserPid());
//                pfBorder.setPidUserName(user.getRealName());
//            }
            pfBorder.setPidUserName("平台");
            String insertDay = DateUtil.insertDay(pfBorder.getCreateTime());
            pfBorder.setPayTimes(insertDay);
            pfBorder.setPfBorderItems(pfBorderItems);
        }
        return pfBorders;
    }

    /**
     * 异步查询出货订单
     *
     * @author muchaofeng
     * @date 2016/4/7 15:54
     */
    public List<PfBorder> findPfpBorder(long userId, Integer orderStatus, Integer sendType) {
        List<PfBorder> pfBorders = pfBorderMapper.selectByUserPid(userId, orderStatus, sendType);
        String skuValue = PropertiesUtils.getStringValue(SysConstants.INDEX_PRODUCT_IMAGE_MIN);
        for (PfBorder pfBorder : pfBorders) {
            List<PfBorderItem> pfBorderItems = pfBorderItemMapper.selectAllByOrderId(pfBorder.getId());
            for (PfBorderItem pfBorderItem : pfBorderItems) {
                ComSkuImage comSkuImage = skuService.findComSkuImage(pfBorderItem.getSkuId());
                pfBorderItem.setSkuUrl(skuValue + comSkuImage.getImgUrl());
                pfBorder.setTotalQuantity(pfBorder.getTotalQuantity() + pfBorderItem.getQuantity());//订单商品总量
                pfBorderItem.setSkuMoney(pfBorderItem.getUnitPrice().toString());
            }
            pfBorder.setOrderMoney(pfBorder.getOrderAmount().toString());
            pfBorder.setBailMoney(pfBorder.getBailAmount().toString());
            if (pfBorder.getSendType() == 1) {
                pfBorder.setPidUserName("平台");
            } else if (pfBorder.getSendType() == 0 || pfBorder.getSendType() == null) {
                pfBorder.setPidUserName("未选择");
            } else if (pfBorder.getSendType() == 2) {
                pfBorder.setPidUserName("自己发货");
            }
//            if (pfBorder.getUserPid() == 0) {
//                pfBorder.setPidUserName("平台");
//            } else {
//                ComUser user = comUserMapper.selectByPrimaryKey(pfBorder.getUserPid());
//                pfBorder.setPidUserName(user.getRealName());
//            }
            pfBorder.setPfBorderItems(pfBorderItems);
        }
        return pfBorders;
    }

    /**
     * 发货
     *
     * @author muchaofeng
     * @date 2016/4/1 18:12
     */
    @Transactional
    public void deliver(String shipManName, Long orderId, String freight, String shipManId, ComUser user) throws Exception {
        PfBorder pfBorder = pfBorderMapper.selectByPrimaryKey(orderId);
        ComUser comUser = comUserMapper.selectByPrimaryKey(pfBorder.getUserId());
        if (pfBorder.getSendType() == 0) {
            throw new BusinessException("请选择发货方式");
        }
        if (freight == null || freight == "") {
            throw new BusinessException("请重新输入快递单号");
        }
        PfBorderItem pfBorderItems = pfBorderItemMapper.selectByOrderId(pfBorder.getId());
        ComAgentLevel comAgentLevel = comAgentLevelMapper.selectByPrimaryKey(pfBorderItems.getAgentLevelId());
        if (pfBorder.getSendType() == 1) {//平台代发
            if (pfBorder.getOrderType() == 2) {//拿货
                pfBorder.setShipStatus(5);
                pfBorder.setOrderStatus(8);
                pfBorder.setIsShip(1);
                PfBorderFreight pfBorderFreight = new PfBorderFreight();
                pfBorderFreight.setCreateTime(new Date());
                pfBorderFreight.setShipManId(Integer.parseInt(shipManId));
                pfBorderFreight.setPfBorderId(orderId);
                pfBorderFreight.setFreight(freight);
                pfBorderFreight.setShipManName(shipManName);
                borderSkuStockService.updateStock(pfBorder, user);
                pfBorderMapper.updateById(pfBorder);
                pfBorderFreightMapper.insert(pfBorderFreight);
                //添加订单日志
                bOrderOperationLogService.insertBOrderOperationLog(pfBorder, "订单完成");

                String url = PropertiesUtils.getStringValue("web.domain.name.address") + "/borderManage/deliveryBorderDetils.html?id=" + pfBorder.getId().toString();
                String[] params = new String[5];
                params[0] = pfBorderItems.getSkuName();
                params[1] = comAgentLevel.getName();
                params[2] = pfBorder.getOrderCode();
                params[3] = shipManName;
                params[4] = freight;
                Boolean aBoolean = WxPFNoticeUtils.getInstance().orderShippedNotice(comUser, params, url);
                if (aBoolean == false) {
                    throw new Exception("订单发货微信提示失败");
                }
                MobileMessageUtil.getInitialization("B").goodsOrderShipRemind(comUser.getMobile(), pfBorder.getOrderCode(), shipManName, freight);
            }
        } else if (pfBorder.getSendType() == 2) {//自己发货
            pfBorder.setShipStatus(5);
            pfBorder.setOrderStatus(8);
            pfBorder.setIsShip(1);
            PfBorderFreight pfBorderFreight = new PfBorderFreight();
            pfBorderFreight.setCreateTime(new Date());
            pfBorderFreight.setPfBorderId(orderId);
            pfBorderFreight.setFreight(freight);
            pfBorderFreight.setShipManName(shipManName);
            pfBorderMapper.updateById(pfBorder);
            pfBorderFreightMapper.insert(pfBorderFreight);
            //添加订单日志
            bOrderOperationLogService.insertBOrderOperationLog(pfBorder, "订单完成");
            String url = PropertiesUtils.getStringValue("web.domain.name.address") + "/borderManage/deliveryBorderDetils.html?id=" + pfBorder.getId().toString();
            String[] params = new String[5];
            params[0] = pfBorderItems.getSkuName();
            params[1] = comAgentLevel.getName();
            params[2] = pfBorder.getOrderCode();
            params[3] = shipManName;
            params[4] = freight;
            Boolean aBoolean = WxPFNoticeUtils.getInstance().orderShippedNotice(comUser, params, url);
            if (aBoolean == false) {
                throw new Exception("订单发货微信提示失败");
            }
            MobileMessageUtil.getInitialization("B").goodsOrderShipRemind(comUser.getMobile(), pfBorder.getOrderCode(), shipManName, freight);
        }
    }

    /**
     * 收货
     *
     * @author muchaofeng
     * @date 2016/4/1 18:42
     */

    @Transactional
    public void closeDeal(Long orderId, ComUser user) throws Exception {
        PfBorder pfBorder = pfBorderMapper.selectByPrimaryKey(orderId);
        if (pfBorder.getSendType() == 1) {//平台代发
            if (pfBorder.getOrderType() == 2) {//拿货
                pfBorder.setOrderStatus(3);
                pfBorder.setShipStatus(9);
//                borderSkuStockService.updateGetStock(pfBorder, user);
                pfBorderMapper.updateById(pfBorder);
                //comUserAccountService.countingByOrder(pfBorder);
            }
        } else if (pfBorder.getSendType() == 2) {//自己发货
            pfBorder.setOrderStatus(3);
            pfBorder.setShipStatus(9);
            pfBorderMapper.updateById(pfBorder);
            //comUserAccountService.countingByOrder(pfBorder);
        }
    }


    /**
     * 订单完成处理统一入口
     *
     * @author ZhaoLiang
     * @date 2016/4/9 11:22
     */
    @Transactional
    public void completeBOrder(PfBorder pfBorder) {
        if (pfBorder == null) {
            throw new BusinessException("订单为空对象");
        }
        if (pfBorder.getPayStatus() != 1) {
            throw new BusinessException("订单还未支付怎么能完成呢？");
        }
        //拿货方式(0未选择1平台代发2自己发货)
        if (pfBorder.getSendType() == 1) {
            if (!pfBorder.getOrderStatus().equals(BOrderStatus.accountPaid.getCode())
                    && !pfBorder.getOrderStatus().equals(BOrderStatus.Ship.getCode())) {
                throw new BusinessException("订单状态异常");
            }
        } else if (pfBorder.getSendType() == 2) {
            if (!pfBorder.getOrderStatus().equals(BOrderStatus.Ship.getCode())
                    && !pfBorder.getOrderStatus().equals(BOrderStatus.Ship.getCode())) {
                throw new BusinessException("订单状态异常");
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
        if (pfBorder.getOrderType() == 0 || pfBorder.getOrderType() == 1) {
            comUserAccountService.countingByOrder(pfBorder);
        }
        logger.info("---实时显示统计-------------------------start");
        List<PfBorderItem> ordItems =  pfBorderItemMapper.getPfBorderItemDetail(pfBorder.getId());
        logger.info("---实时显示统计---------------------------end");
        statisticsByOrder(pfBorder,ordItems);
        logger.info("---结算------------------------------------start");
        updateDisBillAmount(pfBorder,ordItems);
        logger.info("---结算-----------------------------------end");
    }

    private void statisticsByOrder(PfBorder order,List<PfBorderItem> ordItems){
        statisticsUserInfo(order,ordItems);
        statisticsPidUserInfo(order,ordItems);
    }
    private void statisticsUserInfo(PfBorder order,List<PfBorderItem> ordItems){
        logger.info("代理人统计-----start");
        //代理人的统计信息
        Long userId = order.getUserId();
        logger.info("代理人统计-----userId----"+order.getUserId());
        if (userId != null){
            for (PfBorderItem pfBorderItem : ordItems){
                logger.info("代理人统计-----userId----"+order.getUserId()+"----skuId---"+pfBorderItem.getSkuId());
                PfUserStatistics statistics = userStatisticsService.selectByUserIdAndSkuId(userId,pfBorderItem.getSkuId());
                if (statistics!=null){
                    //总成本(订单金额-保证金)
                    BigDecimal ordAmount = order.getOrderAmount();
                    BigDecimal bailAmount = order.getBailAmount();
                    logger.info("总成本---之前----"+statistics.getCostFee());
                    if (statistics.getCostFee()!=null){
                        statistics.setCostFee(statistics.getCostFee().add(ordAmount.subtract(bailAmount)));
                    }else{
                        statistics.setCostFee(ordAmount.subtract(bailAmount));
                    }
                    logger.info("总成本---之后----"+statistics.getCostFee());
                    logger.info("总成本---增加了----"+ordAmount.subtract(bailAmount).intValue());
                    //进货订单数量
                    logger.info("进货订单数量----之前----"+statistics.getUpOrderCount());
                    if (statistics.getUpOrderCount()!=null){
                        statistics.setUpOrderCount(statistics.getUpOrderCount()+1);
                    }else{
                        statistics.setUpOrderCount(1);
                    }
                    logger.info("进货订单数量----之后----"+statistics.getUpOrderCount());
                    //进货商品数量
                    logger.info("进货商品数量----之前---"+statistics.getUpProductCount());
                    if (statistics.getUpProductCount()!=null){
                        statistics.setUpProductCount(statistics.getUpProductCount()+pfBorderItem.getQuantity());
                    }else{
                        statistics.setUpProductCount(pfBorderItem.getQuantity());
                    }
                    logger.info("进货商品数量----之后---"+statistics.getUpProductCount());
                    logger.info("进货商品数量----增加了---"+pfBorderItem.getQuantity());
                    statistics.setCreateTime(new Date());
                    int i = userStatisticsService.updateByIdAndVersion(statistics);
                    if (i!=1){
                        logger.info("更新代理人的统计信息----userId---"+userId+"-----skuId---"+pfBorderItem.getSkuId());
                        throw new BusinessException("更新代理人的统计信息----userId---"+userId+"-----skuId---"+pfBorderItem.getSkuId());
                    }
                }else{
                    statistics = new PfUserStatistics();
                    statistics.setCreateTime(new Date());
                    statistics.setUserId(order.getUserId());
                    statistics.setSkuId(pfBorderItem.getSkuId().longValue());
                    statistics.setIncomeFee(new BigDecimal(0));
                    statistics.setProfitFee(new BigDecimal(0));
                    BigDecimal ordAmount = order.getOrderAmount();
                    BigDecimal bailAmount = order.getBailAmount();
                    statistics.setCostFee(ordAmount.subtract(bailAmount));
                    statistics.setUpOrderCount(1);
                    statistics.setUpProductCount(pfBorderItem.getQuantity());
                    statistics.setDownProductCount(0);
                    statistics.setDownProductCount(0);
                    statistics.setTakeOrderCount(0);
                    statistics.setTakeProductCount(0);
                    statistics.setTakeFee(new BigDecimal(0));
                    statistics.setVersion(0L);
                    userStatisticsService.insert(statistics);
                    logger.info("插入代理人的统计信息-------");
                }
            }
        }else{
            throw new BusinessException("代理人id为null");
        }
        logger.info("代理人统计-----end");
    }
    private void statisticsPidUserInfo(PfBorder order,List<PfBorderItem> ordItems){
        logger.info("代理人上级统计-------start");
        //获得代理人的上级统计信息
        logger.info("代理人上级统计-------userPid---"+order.getUserPid());
        Long userPid = order.getUserPid();
        PfUserStatistics statistics = null;
        if (userPid != null){
            for (PfBorderItem pfBorderItem : ordItems) {
                logger.info("代理人上级统计-------userPid---"+order.getUserPid()+"----skuId----"+pfBorderItem.getSkuId());
                statistics = userStatisticsService.selectByUserIdAndSkuId(userPid,pfBorderItem.getSkuId());
                if (statistics != null){
                    //总销售额
                    BigDecimal ordAmount = order.getOrderAmount();
                    BigDecimal bailAmount = order.getBailAmount();
                    logger.info("总销售额-----之前----"+statistics.getIncomeFee());
                    if (statistics.getIncomeFee()!=null){
                        statistics.setIncomeFee(statistics.getIncomeFee().add(ordAmount.subtract(bailAmount)));
                    }else{
                        statistics.setIncomeFee(ordAmount.subtract(bailAmount));
                    }
                    logger.info("总销售额-----之后----"+statistics.getIncomeFee());
                    logger.info("总销售额-----增加了----"+ordAmount.subtract(bailAmount).intValue());
                    //利润
                    logger.info("利润-----之前----"+statistics.getProfitFee());
                    BigDecimal sumProfitFee = getSumProfitFee(userPid,pfBorderItem.getSkuId(),pfBorderItem.getUnitPrice(),pfBorderItem.getQuantity());
                    if (statistics.getProfitFee()!=null){
                        statistics.setProfitFee(statistics.getProfitFee().add(sumProfitFee));
                    }else{
                        statistics.setProfitFee(sumProfitFee);
                    }
                    logger.info("利润-----之后----"+statistics.getProfitFee());
                    logger.info("利润-----增加了----"+sumProfitFee);
                    //出货订单数量
                    if (statistics.getDownOrderCount()!=null){
                        statistics.setDownOrderCount(statistics.getDownOrderCount()+1);
                    }else{
                        statistics.setDownOrderCount(1);
                    }
                    //出货商品数量
                    logger.info("出货商品数量----之前------"+statistics.getDownProductCount());
                    if (statistics.getDownProductCount()!=null){
                        statistics.setDownProductCount(statistics.getDownProductCount()+pfBorderItem.getQuantity());
                    }else{
                        statistics.setDownProductCount(pfBorderItem.getQuantity());
                    }
                    logger.info("出货商品数量----之后-----"+statistics.getDownProductCount());
                    logger.info("出货商品数量----增加-----"+pfBorderItem.getQuantity());
                    statistics.setCreateTime(new Date());
                    int i = userStatisticsService.updateByIdAndVersion(statistics);
                    if (i!=1){
                        throw new BusinessException("更新代理人上级统计信息失败-----pidUserId---"+order.getUserPid()+"---skuId----"+pfBorderItem.getSkuId());
                    }
                }else{
                    statistics = new PfUserStatistics();
                    statistics.setCreateTime(new Date());
                    statistics.setUserId(order.getUserId());
                    statistics.setSkuId(pfBorderItem.getSkuId().longValue());
                    BigDecimal ordAmount = order.getOrderAmount();
                    BigDecimal bailAmount = order.getBailAmount();
                    statistics.setIncomeFee(ordAmount.subtract(bailAmount));
                    BigDecimal sumProfitFee = getSumProfitFee(userPid,pfBorderItem.getSkuId(),pfBorderItem.getUnitPrice(),pfBorderItem.getQuantity());
                    statistics.setProfitFee(sumProfitFee);
                    statistics.setCostFee(new BigDecimal(0));
                    statistics.setUpOrderCount(0);
                    statistics.setUpProductCount(0);
                    statistics.setDownOrderCount(1);
                    statistics.setDownProductCount(pfBorderItem.getQuantity());
                    statistics.setTakeOrderCount(0);
                    statistics.setTakeProductCount(0);
                    statistics.setTakeFee(new BigDecimal(0));
                    statistics.setVersion(0L);
                    userStatisticsService.insert(statistics);
                    logger.info("插入代理人上级统计信息-------");
                }
            }
        }
        logger.info("代理人上级统计-------end");
    }

    /**
     * 获得商品的利润
     * @param userPid
     * @param skuId
     * @param unitPrice
     * @param quantity
     * @return
     */
    private BigDecimal getSumProfitFee(Long userPid,Integer skuId,BigDecimal unitPrice,Integer quantity){
        PfUserSku pUserSku = null;
        PfSkuAgent pSkuAgent = null;
        BigDecimal sumProfitFee = BigDecimal.ZERO;
        pUserSku = pfUserSkuMapper.selectByUserIdAndSkuId(userPid, skuId);
        pSkuAgent = pfSkuAgentMapper.selectBySkuIdAndLevelId(skuId, pUserSku.getAgentLevelId());
        BigDecimal unit_profit = BigDecimal.ZERO;
        if (pSkuAgent!=null&&pSkuAgent.getUnitPrice()!=null){
            if (unitPrice.compareTo(pSkuAgent.getUnitPrice())<0){
                logger.info("商品的购买价格小于商品的代理价格,利润小于0------userPid---"+userPid+"-----skuId----"+skuId);
                throw new BusinessException("商品的购买价格小于商品的代理价格,利润小于0------userPid---"+userPid+"-----skuId----"+skuId);
            }
            unit_profit= unitPrice.subtract(pSkuAgent.getUnitPrice());
        }else{
            unit_profit= unitPrice;
        }
        sumProfitFee = sumProfitFee.add(unit_profit.multiply(BigDecimal.valueOf(quantity)));
        return sumProfitFee;
    }
    /**
     * 更新用户账户结算中
     * @author hanzengzhi
     * @date 2016/6/7 9:59
     */
    private void updateDisBillAmount(PfBorder order,List<PfBorderItem> ordItems){
        logger.info("更新用户账户结算中------start");
        ComUserAccount comUserAccount = comUserAccountService.findAccountByUserid(order.getUserId());
        if (comUserAccount != null){
            BigDecimal sumProfitFee = BigDecimal.ZERO;
            for (PfBorderItem orderItem : ordItems){
                sumProfitFee = sumProfitFee.add(getSumProfitFee(order.getUserId(),orderItem.getSkuId(),orderItem.getUnitPrice(),orderItem.getQuantity()));
            }
            logger.info("结算中-----之前----"+comUserAccount.getAgentBillAmount());
            if (comUserAccount.getAgentBillAmount()!=null){
                comUserAccount.setAgentBillAmount(comUserAccount.getAgentBillAmount().add(sumProfitFee));
            }else{
                comUserAccount.setAgentBillAmount(sumProfitFee);
            }
            logger.info("结算中-----之后----"+comUserAccount.getAgentBillAmount());
            logger.info("结算中-----增加----"+sumProfitFee);
            int i = comUserAccountService.updateByIdWithVersion(comUserAccount);
            if(i!=1){
                throw new BusinessException("更新结算中数据失败-----用户id-----"+order.getUserId());
            }
        }
        logger.info("更新用户账户结算中------end");
    }
    /**
     * 获取排队订单数量
     *
     * @param skuId
     * @return
     */
    public Integer selectQueuingOrderCount(Integer skuId) {
        return pfBorderMapper.selectQueuingOrderCount(skuId);
    }

    /**
     * 根据userId 和SkuId获取订单信息
     */
    public PfBorder getPfBorderBySkuAndUserId(Integer skuId, Long userId) {
        return pfBorderMapper.selectPfBOrderBySkuIdAndUserId(skuId, userId);
    }
}
