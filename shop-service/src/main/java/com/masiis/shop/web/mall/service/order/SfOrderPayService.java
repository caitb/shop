package com.masiis.shop.web.mall.service.order;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.common.util.MobileMessageUtil;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.mall.order.SfOrderPaymentMapper;
import com.masiis.shop.dao.platform.product.PfSkuAgentMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuMapper;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.common.beans.wx.wxpay.WxPaySysParamReq;
import com.masiis.shop.common.constant.mall.SysConstants;
import com.masiis.shop.web.platform.service.product.PfUserSkuStockService;
import com.masiis.shop.web.common.service.SkuService;
import com.masiis.shop.web.mall.service.shop.SfShopSkuService;
import com.masiis.shop.web.mall.service.shop.SfShopStatisticsService;
import com.masiis.shop.web.common.service.ComUserAccountService;
import com.masiis.shop.web.common.utils.wx.WxSFBeanUtils;
import com.masiis.shop.web.common.utils.wx.WxPFNoticeUtils;
import com.masiis.shop.web.common.utils.wx.WxSFNoticeUtils;
import com.masiis.shop.web.mall.service.user.SfUserAccountService;
import com.masiis.shop.web.mall.service.user.SfUserRelationService;
import com.masiis.shop.web.mall.service.user.SfUserStatisticsService;
import com.masiis.shop.web.common.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by hzz on 2016/4/11.
 * <p/>
 * 订单支付
 */
@Service
public class SfOrderPayService {

    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private SfOrderService ordService;
    @Resource
    private SfOrderOperationLogService ordOperLogService;
    @Resource
    private SfOrderConsigneeService ordConService;
    @Resource
    private SfOrderItemService ordItemService;
    @Resource
    private SfOrderPaymentMapper paymentMapper;
    @Resource
    private SfOrderPaymentService ordPaymentSer;
    @Resource
    private PfUserSkuStockService skuStockService;
    @Resource
    private SfUserRelationService sfUserRelationService;
    @Resource
    private UserService userService;
    @Resource
    private SfShopSkuService shopSkuService;
    @Resource
    private SfOrderItemDistributionService  ordItemDisService;
    @Resource
    private SfUserStatisticsService statisticsService;
    @Resource
    private SfShopStatisticsService shopStatisticsService;
    @Resource
    private PfUserSkuMapper pfUserSkuMapper;
    @Resource
    private PfSkuAgentMapper pfSkuAgentMapper;
    @Resource
    private ComUserAccountService comUserAccountService;
    @Resource
    private SfUserAccountService sfUserAccountService;
    @Resource
    private SkuService skuService;

    /**
     * 获得需要支付的订单的信息
     *
     * @author hanzengzhi
     * @date 2016/4/11 19:45
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Map<String, Object> getOrderInfo(Long orderId) {
        Map<String, Object> map = new HashMap<String, Object>();
        SfOrder order = getOrderById(orderId);
        if (order == null) {
            throw new BusinessException("跳转到支付页面获取订单失败");
        }
        List<SfOrderItem> orderItems = getOrderItem(orderId);
        if (orderItems == null) {
            throw new BusinessException("跳转到支付页面获取子订单详情失败");
        }
        map.put("order", order);
        map.put("orderItems", orderItems);
        return map;
    }

    /**
     * 订单支付成功修改状态
     *
     * @author hanzengzhi
     * @date 2016/4/10 11:21
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void ordPaySuccModStatus(SfOrderPayment orderPayment, String outOrderId) {
        log.info("微信支付完进行异步回调----start");
        try {
            //更新支付订单
            log.info("更新支付订单----start");
            orderPayment.setOutOrderId(outOrderId);
            orderPayment.setIsEnabled(1);//设置为有效
            int i = updateOrderPayment(orderPayment);
            if (i == 1) {
                log.info("更新支付订单成功----end");
            } else {
                log.info("更新支付订单失败----end");
                throw new BusinessException("更新支付订单失败----end");
            }
            //更新订单
            SfOrder order = ordService.getOrderById(orderPayment.getSfOrderId());
            log.info("更新订单----start");
            int ii = updateOrder(order, orderPayment);
            if (ii == 1) {
                log.info("更新订单成功----end");
            } else {
                log.info("更新订单失败----end");
                throw new BusinessException("更新订单失败----end");
            }
            //插入订单操作日志
            log.info("插入订单操作日志---start");
            int iii = insertOrdOperLog(orderPayment);
            if (iii == 1) {
                log.info("插入订单操作日志成功----end");
            } else {
                log.info("插入订单操作日志失败----end");
                throw new BusinessException("更新订单操作日志失败----end");
            }
            //更新库存
            log.info("支付成功更新库存---start");
            List<SfOrderItem> orderItems = ordItemService.getOrderItemByOrderId(order.getId());
            if (orderItems != null && orderItems.size() != 0) {
                updateStock(order, orderItems);
            }
            log.info("支付成功更新库存---end");

            //更新小铺商品的总销量
            Integer isOwnShip = null;
            if (order.getSendMan()==0){
                //平台发货
                isOwnShip = 0;
            }else{
                //店主发货
                isOwnShip = 1;
            }
            for (SfOrderItem orderItem : orderItems) {
                updateShopSku(order.getShopId(), orderItem.getSkuId(), orderItem.getQuantity(),isOwnShip);
            }
            //更新sfuserRelation中isBuy字段
            updateRelationIsBuy(order.getUserId(),order.getShopId());
            //统计更新信息
            updateStatistics(order,orderItems);
            //更新小铺用户结算中信息
            updateDisBillAmount(order,orderItems);
            //微信短信提醒
            ComUser comUser = userService.getUserById(order.getUserId());
            orderNotice(comUser, order, orderItems);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
        log.info("微信支付完进行异步回调---end");
    }

    private void updateStatistics(SfOrder order,List<SfOrderItem> orderItems){
        log.info("统计信息--------------------------start");
        updatePurchaseUserStatistics(order,orderItems);
        updateShopUserStatistics(order,orderItems);
        updateDisUserStatistics(order,orderItems);
        log.info("统计信息-----------------------end");
    }
    /**
     * 更新购买人的统计信息
     * @author hanzengzhi
     * @date 2016/6/7 14:31
     */
    private void updatePurchaseUserStatistics(SfOrder order,List<SfOrderItem> orderItems){
        log.info("统计购买人-------start");
        log.info("购买人id----------"+order.getUserId());
        SfUserStatistics statistics = statisticsService.selectByUserId(order.getUserId());
        if (statistics != null){
            //总订单数
            log.info("总订单数-----之前-----"+statistics.getOrderCount());
            if (statistics.getOrderCount()!=null){
                statistics.setOrderCount(statistics.getOrderCount()+1);
            }else{
                statistics.setOrderCount(1);
            }
            log.info("总订单数-----之后-----"+statistics.getOrderCount());
            //总购买金额(总购买金额 = 总共买金额 + 订单金额)
            log.info("总购买金额------之前-----"+statistics.getBuyFee());
            if (statistics.getBuyFee()!=null){
                statistics.setBuyFee(statistics.getBuyFee().add(order.getPayAmount()));
            }else{
                statistics.setBuyFee(order.getPayAmount());
            }
            log.info("总购买金额------之后-----"+statistics.getBuyFee());
            log.info("总购买金额------增加-----"+order.getPayAmount().intValue());
            int i = statisticsService.updateByIdAndVersion(statistics);
            if (i != 1){
                log.info("更新购买人统计信息失败------购买人id---"+order.getUserId());
                throw new BusinessException("更新购买人统计信息失败------购买人id---"+order.getUserId());
            }
        }else{
            log.info("sfUserStatistics为null---------------");
            throw new BusinessException("sfUserStatistics为null---------------");
        }
        log.info("统计购买人-------end");
    }
    private void updateShopUserStatistics(SfOrder order,List<SfOrderItem> orderItems){
        log.info("小铺统计信息-------start");
        //获得小铺统计信息
        log.info("小铺统计信息-------用户id------"+order.getShopUserId());
        SfShopStatistics shopStatistics = shopStatisticsService.selectByShopUserId(order.getShopUserId());
        if (shopStatistics != null){
            //总销售额(总销售额 = 订单金额)
            log.info("总销售额----------之前------"+shopStatistics.getIncomeFee());
            if (shopStatistics.getIncomeFee()!=null){
                shopStatistics.setIncomeFee(shopStatistics.getIncomeFee().add(order.getPayAmount()));
            }else{
                shopStatistics.setIncomeFee(order.getPayAmount());
            }
            log.info("总销售额----------之后------"+shopStatistics.getIncomeFee());
            log.info("总销售额----------增加了------"+order.getPayAmount().intValue());
            //总利润
            log.info("总利润---------之前------"+shopStatistics.getProfitFee());
            BigDecimal sumProfitFee = getShopProfitfee(order,orderItems);
            if (shopStatistics.getProfitFee()!=null){
                shopStatistics.setProfitFee(shopStatistics.getProfitFee().add(sumProfitFee));
            }else{
                shopStatistics.setProfitFee(sumProfitFee);
            }
            log.info("总利润---------之后------"+shopStatistics.getProfitFee());
            log.info("总利润---------增加了------"+sumProfitFee.intValue());
            //店铺总订单
            if (shopStatistics.getOrderCount()!=null){
                shopStatistics.setOrderCount(shopStatistics.getOrderCount()+1);
            }else{
                shopStatistics.setOrderCount(1);
            }
            //店铺总销量
            log.info("店铺总销量------之前----"+shopStatistics.getProductCount());
            Integer toatalQuantity = new Integer(0);
            for (SfOrderItem orderItem : orderItems){
                toatalQuantity = toatalQuantity + orderItem.getQuantity();
            }
            if (shopStatistics.getProductCount()!=null){
                shopStatistics.setProductCount(shopStatistics.getProductCount()+toatalQuantity);
            }else{
                shopStatistics.setProductCount(toatalQuantity);
            }
            log.info("店铺总销量------之后----"+shopStatistics.getProductCount());
            log.info("店铺总销量------增加了----"+toatalQuantity);
            shopStatisticsService.updateByIdAndVersion(shopStatistics);
        }
        log.info("小铺统计信息-------end");
    }
    private void updateDisUserStatistics(SfOrder order,List<SfOrderItem> orderItems){
        log.info("更新获得三级分润人的信息------start");
        //总分润(一条订单分润人的分润信息的更新)
        for (SfOrderItem orderItem : orderItems){
            List<SfOrderItemDistribution> itemDises = ordItemDisService.selectBySfOrderItemId(orderItem.getId());
            for (SfOrderItemDistribution itemDis : itemDises){
                SfUserStatistics disUserStatist =  statisticsService.selectByUserId(itemDis.getUserId());
                log.info("分润人id------------"+itemDis.getUserId());
                if (disUserStatist != null ){
                    log.info("分润------之前------"+disUserStatist.getDistributionFee());
                    if (disUserStatist.getDistributionFee()!=null){
                        disUserStatist.setDistributionFee(disUserStatist.getDistributionFee().add(itemDis.getDistributionAmount()));
                    }else{
                        disUserStatist.setDistributionFee(itemDis.getDistributionAmount());
                    }
                    log.info("分润------之后------"+disUserStatist.getDistributionFee());
                    log.info("分润------增加了------"+itemDis.getDistributionAmount().intValue());
                    int i = statisticsService.updateByIdAndVersion(disUserStatist);
                    if (i != 1){
                        log.info("更新分润信息失败----分润人id---"+itemDis.getUserId()+"---小铺订单子表id---"+itemDis.getSfOrderItemId());
                        throw new BusinessException("更新分润信息失败----分润人id---"+itemDis.getUserId()+"---小铺订单子表id---"+itemDis.getSfOrderItemId());
                    }
                }
            }
        }
        log.info("更新获得三级分润人的信息------end");
    }

    private void updateDisBillAmount(SfOrder order,List<SfOrderItem> orderItems){
        updateShopUserDisBillAmount(order,orderItems);
        updateDisUserBillAmount(order,orderItems);
    }
    /**
     * 更新小铺用户人结算中信息
     * 结算中(结算中 = 之前结算中 + (商品的购买价格 - 商品的分润 -  代理运费 ) )
     * @param order
     * @param orderItems
     */
    private void updateShopUserDisBillAmount(SfOrder order,List<SfOrderItem> orderItems){
        log.info("更新小铺用户结算信息--------start");
        log.info("更新小铺用户结算信息--------小铺用户id-----------"+order.getShopUserId());
        ComUserAccount comUserAccount = comUserAccountService.findAccountByUserid(order.getShopUserId());
        if (comUserAccount != null){
            BigDecimal payAmount = order.getPayAmount();
            BigDecimal disAmount = order.getDistributionAmount();
            BigDecimal agentShipAmount = order.getAgentShipAmount();
            BigDecimal billAmount = payAmount.subtract(disAmount).subtract(agentShipAmount);
            log.info("小铺结算中-----之前------"+comUserAccount.getDistributionBillAmount());
            if (comUserAccount.getDistributionBillAmount()!=null){
                comUserAccount.setDistributionBillAmount(comUserAccount.getDistributionBillAmount().add(billAmount));
            }else{
                comUserAccount.setDistributionBillAmount(billAmount);
            }
            log.info("小铺结算中-----之后------"+comUserAccount.getDistributionBillAmount());
            log.info("小铺结算中-----增加了------"+billAmount);
            int i = comUserAccountService.updateByIdWithVersion(comUserAccount);
            if (i!=1){
                log.info("更新分润结算失败------订单id---"+order.getId()+"----分润结算账户id---"+order.getShopUserId());
                throw new BusinessException("更新分润结算失败------订单id---"+order.getId()+"----分润结算账户id---"+order.getShopUserId());
            }
        }
        log.info("更新小铺用户结算信息--------end");
    }
    /**
     * 更新订单分润人的结算信息 ( 结算中 = 之前的结算 + 获得分润)
     * @author hanzengzhi
     * @date 2016/6/7 10:24
     */
    private void updateDisUserBillAmount(SfOrder order,List<SfOrderItem> orderItems){
        log.info("更新订单三级人润的结算中------------------start");
        for (SfOrderItem orderItem : orderItems){
            List<SfOrderItemDistribution> itemDises = ordItemDisService.selectBySfOrderItemId(orderItem.getId());
            for (SfOrderItemDistribution itemDis : itemDises){
                log.info("---------分润人id---------"+itemDis.getUserId());
                SfUserAccount sfUserAccount = sfUserAccountService.findAccountByUserId(itemDis.getUserId());
                if (sfUserAccount!=null){
                    log.info("结算中-----之前-------------"+sfUserAccount.getCountingFee());
                    if (sfUserAccount.getCountingFee()!=null){
                        sfUserAccount.setCountingFee(sfUserAccount.getCountingFee().add(itemDis.getDistributionAmount()));
                    }else{
                        sfUserAccount.setCountingFee(itemDis.getDistributionAmount());
                    }
                    log.info("结算中-----之后-------------"+sfUserAccount.getCountingFee());
                    log.info("结算中-----增加了-------------"+itemDis.getDistributionAmount());
                    int i = sfUserAccountService.updateByIdAndVersion(sfUserAccount);
                    if (i!=1){
                        log.info("更新分润结算失败---订单id---"+order.getId()+"---分润人id----"+itemDis.getUserId());
                        throw new BusinessException("更新分润结算失败---订单id---"+order.getId()+"---分润人id----"+itemDis.getUserId());
                    }
                }
            }
        }
        log.info("更新订单三级人润的结算中------------------end");
    }

    /**
     * 此订单小铺获得利润
     * 小铺商品的利润 = 商品购买价格 - 商品的代理价格 - 商品的分润 - 代理运费
     * @param order
     * @param orderItems
     * @return
     */
    private BigDecimal getShopProfitfee(SfOrder order,List<SfOrderItem> orderItems){
        BigDecimal sumProfitFee = BigDecimal.ZERO;
        PfUserSku pUserSku = null;
        PfSkuAgent pSkuAgent = null;
        for (SfOrderItem orderItem:orderItems){
            pUserSku = pfUserSkuMapper.selectByUserIdAndSkuId(order.getShopUserId(), orderItem.getSkuId());
            pSkuAgent = pfSkuAgentMapper.selectBySkuIdAndLevelId(orderItem.getSkuId(), pUserSku.getAgentLevelId());
            BigDecimal unit_profit = BigDecimal.ZERO;
            if (orderItem.getUnitPrice()==null){
                log.info("orderItem订单明细中购买价格为null");
                throw new BusinessException("orderItem订单明细中购买价格为null");
            }
            if (pSkuAgent!=null&&pSkuAgent.getUnitPrice()!=null){
                if (orderItem.getUnitPrice().compareTo(pSkuAgent.getUnitPrice())<0){
                    log.info("商品的购买价格小于商品的拿货价格-----商品的skuId--"+orderItem.getSkuId()+"-----orderItem订单明细id---"+orderItem.getId());
                    throw new BusinessException("商品的购买价格小于商品的拿货价格-----商品的skuId--"+orderItem.getSkuId()+"-----orderItem订单明细id---"+orderItem.getId());
                }
                unit_profit  = orderItem.getUnitPrice().subtract(pSkuAgent.getUnitPrice());
            }else{
                log.info("商品的拿货价格为null-----商品skuId-----"+orderItem.getSkuId());
                throw new BusinessException("商品的拿货价格为null-----商品skuId-----"+orderItem.getSkuId());
            }
            sumProfitFee = sumProfitFee.add(unit_profit.multiply(BigDecimal.valueOf(orderItem.getQuantity())));
        }
        log.info("商品获得还没有减去分润和代理运费的利润----------"+sumProfitFee);
        log.info("商品分润---------"+order.getDistributionAmount());
        log.info("商品代理运费---------"+order.getAgentShipAmount());
        if (order.getAgentShipAmount()!=null){
            sumProfitFee = sumProfitFee.subtract(order.getAgentShipAmount());
        }
        if (order.getDistributionAmount()!=null){
            sumProfitFee = sumProfitFee.subtract(order.getDistributionAmount());
        }
        log.info("商品获得净利润---------------------"+sumProfitFee.intValue());
        if (sumProfitFee.compareTo(new BigDecimal(0))<0){
            log.info("商品获得利润为负数-------订单id------"+order.getId());
            throw new BusinessException("商品获得利润为负数-------订单id------"+order.getId());
        }
        return sumProfitFee;
    }

    /**
     * 更新支付订单
     *
     * @author hanzengzhi
     * @date 2016/4/11 10:34
     */
    private int updateOrderPayment(SfOrderPayment orderPayment) {
        return ordPaymentSer.updateOrderPayment(orderPayment);
    }

    /**
     * 更新订单状态
     *
     * @author hanzengzhi
     * @date 2016/4/10 11:47
     */
    private int updateOrder(SfOrder order, SfOrderPayment orderPayment) {
        order.setModifyTime(new Date());
        order.setPayTime(new Date());
        order.setOrderStatus(7);//待发货
        order.setPayStatus(1);//已支付
        order.setReceivableAmount(order.getReceivableAmount().subtract(orderPayment.getAmount()));//应收费用
        order.setPayAmount(order.getPayAmount().add(orderPayment.getAmount()));
        return ordService.update(order);
    }

    /**
     * 修改订单的操作日志
     *
     * @author hanzengzhi
     * @date 2016/4/10 12:02
     */
    private int updateOrdOperLog(SfOrderOperationLog ordOperLog) {
        StringBuffer sb = new StringBuffer("将订单的状态由");
        sb.append(ordOperLog.getSfOrderStatus());
        sb.append("修改为").append(1);
        ordOperLog.setSfOrderStatus(1);
        return ordOperLogService.update(ordOperLog);
    }

    /**
     * 插入订单日志
     *
     * @author hanzengzhi
     * @date 2016/4/25 15:55
     */
    private int insertOrdOperLog(SfOrderPayment orderPayment) {
        SfOrderOperationLog ordOperLog = new SfOrderOperationLog();
        ordOperLog.setCreateTime(new Date());
        ordOperLog.setSfOrderId(orderPayment.getSfOrderId());
        ordOperLog.setSfOrderStatus(1);
        ordOperLog.setCreateMan(orderPayment.getSfOrderId());
        ordOperLog.setRemark("订单支付成功,订单状态由0变1");
        int i = ordOperLogService.insert(ordOperLog);
        return i;
    }

    /**
     * 支付成功后更新冻结库存
     *
     * @author hanzengzhi
     * @date 2016/4/14 15:57
     */
    private void updateStock(SfOrder order, List<SfOrderItem> orderItems) {
        for (SfOrderItem orderItem : orderItems) {
            PfUserSkuStock skuStock = skuStockService.selectByUserIdAndSkuId(order.getShopUserId(), orderItem.getSkuId());
            log.info("判断是平台发货还是店主自己发货------order.getSendMan()===="+order.getSendMan());
            if (order.getSendMan()==0){
                //平台发货
                log.info("平台发货----更新库存shopUserId---" + order.getShopUserId() + "商品skuId----" + orderItem.getSkuId() + "之前冻结库存----" + skuStock.getFrozenStock());
                skuStock.setFrozenStock(skuStock.getFrozenStock() + orderItem.getQuantity());
                log.info("平台发货----更新库存shopUserId---" + order.getShopUserId() + "商品skuId----" + orderItem.getSkuId() + "之后冻结库存----" + skuStock.getFrozenStock());
            }else{
                //店主发货
                log.info("店主自己发货----更新库存shopUserId---" + order.getShopUserId() + "商品skuId----" + orderItem.getSkuId() + "之前冻结库存----" + skuStock.getFrozenCustomStock());
                skuStock.setFrozenCustomStock(skuStock.getFrozenCustomStock() + orderItem.getQuantity());
                log.info("店主发货----更新库存shopUserId---" + order.getShopUserId() + "商品skuId----" + orderItem.getSkuId() + "之后冻结库存----" + skuStock.getFrozenCustomStock());
            }
            int i = skuStockService.updateByIdAndVersions(skuStock);
            if (i == 0) {
                log.info("更新库存shopUserId---" + order.getShopUserId() + "商品skuId----" + orderItem.getSkuId() + "失败");
                throw new BusinessException("更新冻结库存失败");
            }
        }
    }

    /**
     * 更新商品小铺的总销量
     *
     * @author hanzengzhi
     * @date 2016/5/11 14:30
     */
    private void updateShopSku(Long shopId, Integer skuId, Integer quantity,Integer isOwnShip) {
        log.info("更新小铺商品表的总销量----start");
        log.info("shopId------" + shopId + "----skuId---" + skuId +"----是否平台发货--isOwnShip-----"+isOwnShip+"----quantity----" + quantity);
        SfShopSku shopSku = shopSkuService.selectByShopIdAndSkuId(shopId, skuId,isOwnShip);
        if (shopSku != null) {
            if (shopSku.getSaleNum() != null) {
                shopSku.setSaleNum(shopSku.getSaleNum() + quantity);
            } else {
                shopSku.setSaleNum(Long.parseLong(quantity + ""));
            }
            int i = shopSkuService.update(shopSku);
            if (i != 1) {
                log.info("更新小铺商品的总销量失败");
                throw new BusinessException("更新小铺商品的总销量失败");
            }
        } else {
            log.info("更新小铺商品的总销量失败:shopSku为null");
            throw new BusinessException("更新小铺商品的总销量失败:shopSku为null");
        }
        log.info("更新小铺商品表的总销量----end");
    }



    /**
     * 支付成功回调
     *
     * @author hanzengzhi
     * @date 2016/5/9 14:36
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Map<String, Object> paySuccessCallBack(Long orderId) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        try {
            //订单的收货地址
            SfOrderConsignee ordCon = getOrdConByOrdId(orderId);
            map.put("orderConsignee", ordCon);
            //订单信息
            SfOrder order = getOrderById(orderId);
            map.put("order", order);
            //获得用户的分销关系的父id
           /* Long userPid = getUserPid(order.getUserId());
            map.put("userPid", userPid);*/
        } catch (Exception e) {
            throw new BusinessException(e);
        }
        return map;
    }


    /**
     * 查看订单详情
     *
     * @author hanzengzhi
     * @date 2016/4/10 13:59
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Map<String, Object> getOrderDetail(Long orderId) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        try {
            //订单的收货地址
            SfOrderConsignee ordCon = getOrdConByOrdId(orderId);
            map.put("orderConsignee", ordCon);
            //订单信息
            SfOrder order = getOrderById(orderId);
            map.put("order", order);
            //运费
            if(order!=null){
                BigDecimal shipAmount = order.getShipAmount();
                if (shipAmount.compareTo(new BigDecimal(0))==0){
                    map.put("isFreeShipAmount","true");
                }else{
                    map.put("isFreeShipAmount","false");
                    map.put("skuTotalShipAmount", shipAmount);
                }
            }
            //订单详情信息
            List<SfOrderItem> orderItems = getOrderItem(orderId);
            map.put("orderItems", orderItems);
            //获得购买人的分销关系
            Long userPid = getUserPid(order.getUserId(),order.getShopId());
            map.put("userPid", userPid);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
        return map;
    }

    /**
     * 获得订单的收货地址
     *
     * @author hanzengzhi
     * @date 2016/4/10 14:03
     */
    private SfOrderConsignee getOrdConByOrdId(Long orderId) {
        return ordConService.getOrdConByOrdId(orderId);
    }

    /**
     * 根据订单id获得订单信息
     *
     * @author hanzengzhi
     * @date 2016/4/10 14:10
     */
    private SfOrder getOrderById(Long orderId) {
        return ordService.getOrderById(orderId);
    }

    /**
     * 根据订单id获得订单的详情信息
     *
     * @author hanzengzhi
     * @date 2016/4/10 14:18
     */
    private List<SfOrderItem> getOrderItem(Long orderId) {
        return ordItemService.getOrderItemByOrderId(orderId);
    }

    /**
     * 获得购买人的分销关系
     *
     * @author hanzengzhi
     * @date 2016/4/14 16:29
     */
    private Long getUserPid(Long userId,Long shopId) {
        log.info("获得购买人的分销关系------userId-----"+userId+"-----shopId-----"+shopId);
        SfUserRelation userRelation = sfUserRelationService.getSfUserRelationByUserIdAndShopId(userId,shopId);
        if (userRelation == null) {
            throw new BusinessException("支付成功后获得分销关系为null");
        } else {
            return userRelation.getUserPid();
        }
    }

    /**
     * 更新是否在小铺中购买过字段 isBuy
     * @param userId
     * @param shopId
     */
    private void updateRelationIsBuy(Long userId,Long shopId){
        log.info("更新是否在小铺中购买过字段 isBuy----start");
        SfUserRelation userRelation = sfUserRelationService.getSfUserRelationByUserIdAndShopId(userId,shopId);
        userRelation.setIsBuy(1);
        int i = sfUserRelationService.updateUserRelation(userRelation);
        if (i==1){
            log.info("更新是否在小铺中购买过成功");
        }else{
            log.info("更新是否在小铺中购买过失败");
            throw new BusinessException("更新是否在小铺中购买过失败");
        }
        log.info("更新是否在小铺中购买过字段 isBuy----end");
    }


    /**
     * 发送微信和短信提醒
     *
     * @author hanzengzhi
     * @date 2016/5/5 12:02
     */
    private void orderNotice(ComUser comUser, SfOrder order, List<SfOrderItem> orderItems) {
        //微信提醒
        log.info("微信提醒--------------------------------start");
        String[] param = new String[]{order.getOrderCode(), "￥" + order.getPayAmount() + "", "微信支付"};
        /*消费者端微信提醒*/
        WxSFNoticeUtils.getInstance().orderCreateNotice(comUser, param);
        /*小铺端归属人微信提醒*/
        log.info("小铺归属人微信提醒-------start");
        ComUser shopUser = userService.getUserById(order.getShopUserId());
        if (shopUser != null) {
            log.info("小铺id------"+shopUser.getId());
            SfOrderConsignee sfOrderConsignee = ordConService.getOrdConByOrdId(order.getId());
            //1,收件人;2,联系电话;3,收货地址;4,购物清单;5,备注
            String[] param_shopuser = new String[5];
            param_shopuser[0] = sfOrderConsignee.getConsignee();
            param_shopuser[1] = sfOrderConsignee.getMobile();
            param_shopuser[2] = sfOrderConsignee.getProvinceName() + sfOrderConsignee.getCityName() + sfOrderConsignee.getRegionName() + sfOrderConsignee.getAddress();
            StringBuilder sb = new StringBuilder();
            int n = 0;
            for (SfOrderItem sfOrderItem : orderItems) {
                if (n == 0) {
                    sb.append(sfOrderItem.getSkuName() + sfOrderItem.getQuantity() + "件");
                } else {
                    sb.append(";" + sfOrderItem.getSkuName() + sfOrderItem.getQuantity() + "件");
                }
                n++;
            }
            param_shopuser[3] = sb.toString();
            param_shopuser[4] = order.getRemark();
            String url = null;
            log.info("小铺发送微信----param参数-----"+param_shopuser.toString());
            if (isEnoughStock(order.getShopUserId(),orderItems)){
                url = PropertiesUtils.getStringValue("web.domain.name.address") + "/sfOrderController/stockShipOrder";
                WxPFNoticeUtils.getInstance().newShopOrderNotice(shopUser, param_shopuser, url);
            }else {
                url = PropertiesUtils.getStringValue("web.domain.name.address") + "/product/user/"+order.getShopUserId();
                WxPFNoticeUtils.getInstance().newShopOrderNoticeNoStock(shopUser, param_shopuser, url);
            }
        }
        log.info("小铺归属人微信提醒-------end");
        log.info("库存不足提醒--------------start");

        log.info("库存不足提醒----------------end");
        /*分润人微信提醒*/
        log.info("分润人微信提醒------start");
        List<SfOrderItemDistribution> ordItemDisList = ordItemDisService.selectBySfOrderId(order.getId());
        for(SfOrderItemDistribution ordItemDis : ordItemDisList){
            log.info("分润人id-------"+ordItemDis.getUserId());
            log.info("分润金额--------"+ordItemDis.getDistributionAmount());
            String[] _param =new String[]{"￥"+ordItemDis.getDistributionAmount(), DateUtil.Date2String(ordItemDis.getCreateTime(),DateUtil.SQL_TIME_FMT)};
            ComUser _comUser=userService.getUserById(ordItemDis.getUserId());
            WxSFNoticeUtils.getInstance().profitInNotice(_comUser,_param,false, SysConstants.MALL_DOMAIN_NAME_ADDRESS +"/sfaccount/rewardHome.shtml");
        }
        log.info("分润人微信提醒------end");
        log.info("微信提醒--------------------------------end");
        //短信提醒
        log.info("短信提醒-------------------------------start");
        /*消费者端提醒*/
        StringBuffer skuNames = new StringBuffer();
        for (SfOrderItem orderItem : orderItems) {
            skuNames.append(orderItem.getSkuName());
        }
        MobileMessageUtil.getInitialization("C").consumerOrderRemind(comUser.getMobile(), skuNames.toString());
        /*小铺归属人提醒*/
        if (shopUser != null) {
            log.info("小铺归属人短信提醒------小铺人的电话-----"+shopUser.getMobile());
            MobileMessageUtil.getInitialization("C").newMallOrderRemind(shopUser.getMobile());
        }
        log.info("短信提醒-------------------------------end");
    }
    private Boolean isEnoughStock(Long shopUserId,List<SfOrderItem> orderItems) {
        log.info("判断商品是否有足够的库存----start");
        for (SfOrderItem orderItem:orderItems){
            PfUserSkuStock skuStock = skuStockService.selectByUserIdAndSkuId(shopUserId, orderItem.getSkuId());
            if (skuStock!=null){
                if (skuStock.getStock()-skuStock.getFrozenStock()<0){
                    log.info("库存不足------skuId----"+orderItem.getSkuId());
                    return false;
                }
            }
        }
        log.info("判断商品是否有足够的库存----end");
        return true;
    }
    public void addSfOrderPayment(SfOrderPayment payment) {
        paymentMapper.insert(payment);
    }

    /**
     * 调用微信支付
     *
     * @author hanzengzhi
     * @date 2016/4/11 10:40
     */
    public WxPaySysParamReq callWechatPay(HttpServletRequest request, String orderCode, Long orderId, Boolean isPay) {
        WxPaySysParamReq wpspr = new WxPaySysParamReq();
        SfOrder order = ordService.getOrderById(orderId);
        if (isPay) {
            if (order == null || !order.getOrderStatus().equals(0)) {
                log.info("调用微信支付失败:订单为null或者订单状态不是未支付状态");
                throw new BusinessException("调用微信支付失败:订单为null或者订单状态不是未支付状态");
            }
        }
        wpspr.setOrderId(orderCode);
        wpspr.setSignType("MD5");
        wpspr.setNonceStr(WxSFBeanUtils.createGenerateStr());
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
        wpspr.setSuccessUrl(basePath + "orderPay/paySuccessCallBack.html?orderId=" + orderId);
        wpspr.setErrorUrl(basePath + "orderPay/getOrderInfo.html?orderId=" + orderId);//微信支付失败回掉
        wpspr.setSign(WxSFBeanUtils.toSignString(wpspr));
        return wpspr;
    }

    public SfOrderPayment findOrderPaymentBySerialNum(String paySerialNum) {
        return paymentMapper.selectBySerialNum(paySerialNum);
    }
}
