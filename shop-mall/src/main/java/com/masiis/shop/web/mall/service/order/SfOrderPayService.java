package com.masiis.shop.web.mall.service.order;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.mall.order.SfOrderPaymentMapper;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.mall.beans.pay.wxpay.WxPaySysParamReq;
import com.masiis.shop.web.mall.service.product.PfUserSkuStockService;
import com.masiis.shop.web.mall.service.user.SfUserRelationService;
import com.masiis.shop.web.mall.utils.WXBeanUtils;
import com.masiis.shop.web.mall.utils.wx.WxSFNoticeUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by hzz on 2016/4/11.
 *
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

    /**
     * 获得需要支付的订单的信息
     * @author hanzengzhi
     * @date 2016/4/11 19:45
     */
    @Transactional(propagation = Propagation.REQUIRED,readOnly = true)
    public Map<String,Object> getOrderInfo(Long orderId){
        Map<String,Object> map = new HashMap<String, Object>();
        SfOrder order = getOrderById(orderId);
        if (order == null){
            throw new BusinessException("跳转到支付页面获取订单失败");
        }
        List<SfOrderItem> orderItems = getOrderItem(orderId);
        if (orderItems == null){
            throw new BusinessException("跳转到支付页面获取子订单详情失败");
        }
        map.put("order",order);
        map.put("orderItems",orderItems);
        return map;
    }

    /**
     * 订单支付成功修改状态
     * @author hanzengzhi
     * @date 2016/4/10 11:21
     */
    @Transactional(propagation = Propagation.REQUIRED,readOnly = false)
    public void ordPaySuccModStatus(SfOrderPayment orderPayment , String outOrderId){
        log.info("微信支付完进行异步回调----start");
        try{
            //更新支付订单
            log.info("更新支付订单----start");
            orderPayment.setOutOrderId(outOrderId);
            orderPayment.setIsEnabled(1);//设置为有效
            int i = updateOrderPayment(orderPayment);
            if (i == 1){
                log.info("更新支付订单成功----end");
            }else{
                log.info("更新支付订单失败----end");
                throw new BusinessException("更新支付订单失败----end");
            }
            //更新订单
            SfOrder order = ordService.getOrderById(orderPayment.getSfOrderId());
            log.info("更新订单----start");
            int ii = updateOrder(order,orderPayment);
            if (ii == 1){
                log.info("更新订单成功----end");
            }else{
                log.info("更新订单失败----end");
                throw new BusinessException("更新订单失败----end");
            }
            //插入订单操作日志
            log.info("插入订单操作日志---start");
            int iii = insertOrdOperLog(orderPayment);
            if (iii ==1 ){
                log.info("插入订单操作日志成功----end");
            }else{
                log.info("插入订单操作日志失败----end");
                throw new BusinessException("更新订单操作日志失败----end");
            }
            //更新库存
            log.info("支付成功更新库存---start");
            List<SfOrderItem> orderItems = ordItemService.getOrderItemByOrderId(order.getId());
            if (orderItems!=null&&orderItems.size()!=0){
                updateStock(order,orderItems);
            }
            log.info("支付成功更新库存---end");
        }catch (Exception e){
            throw new BusinessException(e);
        }
        log.info("微信支付完进行异步回调---end");
    }
    /**
     * 更新支付订单
     * @author hanzengzhi
     * @date 2016/4/11 10:34
     */
    private int updateOrderPayment(SfOrderPayment orderPayment){
        return ordPaymentSer.updateOrderPayment(orderPayment);
    }
    /**
     * 更新订单状态
     * @author hanzengzhi
     * @date 2016/4/10 11:47
     */
    private int updateOrder(SfOrder order,SfOrderPayment orderPayment){
        order.setModifyTime(new Date());
        order.setPayTime(new Date());
        order.setOrderStatus(7);//待发货
        order.setPayStatus(1);//已支付
        order.setReceivableAmount(order.getReceivableAmount().subtract(orderPayment.getAmount()));//应收费用
        order.setPayAmount(order.getPayAmount().add(orderPayment.getAmount()));
        return  ordService.update(order);
    }
    /**
     * 修改订单的操作日志
     * @author hanzengzhi
     * @date 2016/4/10 12:02
     */
    private int updateOrdOperLog(SfOrderOperationLog ordOperLog){
        StringBuffer sb = new StringBuffer("将订单的状态由");
        sb.append(ordOperLog.getSfOrderStatus());
        sb.append("修改为").append(1);
        ordOperLog.setSfOrderStatus(1);
        return ordOperLogService.update(ordOperLog);
    }
    /**
     * 插入订单日志
     * @author hanzengzhi
     * @date 2016/4/25 15:55
     */
    private int insertOrdOperLog(SfOrderPayment orderPayment){
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
     * @author hanzengzhi
     * @date 2016/4/14 15:57
     */
    private void updateStock(SfOrder order,List<SfOrderItem> orderItems){
        for (SfOrderItem orderItem:orderItems){
            PfUserSkuStock skuStock = skuStockService.selectByUserIdAndSkuId(order.getShopUserId(),orderItem.getSkuId());
            log.info("更新库存shopUserId---"+order.getShopUserId()+"商品skuId----"+orderItem.getSkuId()+"之前冻结库存----"+skuStock.getFrozenStock());
            skuStock.setFrozenStock(skuStock.getFrozenStock()+orderItem.getQuantity());
            log.info("更新库存shopUserId---"+order.getShopUserId()+"商品skuId----"+orderItem.getSkuId()+"之后冻结库存----"+skuStock.getFrozenStock());
            int i= skuStockService.update(skuStock);
            if (i == 0){
                log.info("更新库存shopUserId---"+order.getShopUserId()+"商品skuId----"+orderItem.getSkuId()+"失败");
                throw new BusinessException("更新冻结库存失败");
            }
        }
    }


    /**
     * 支付成功回调
     * @author hanzengzhi
     * @date 2016/4/10 13:59
     */
    @Transactional(propagation = Propagation.REQUIRED,readOnly = true)
    public Map<String,Object> paySuccessCallBack(ComUser comUser,Long orderId){
        Map<String,Object> map = new LinkedHashMap<String,Object>();
        try{
            //订单的收货地址
            SfOrderConsignee ordCon = getOrdConByOrdId(orderId);
            map.put("orderConsignee",ordCon);
            //订单信息
            SfOrder order = getOrderById(orderId);
            map.put("order",order);
            //订单详情信息
            List<SfOrderItem> orderItems = getOrderItem(orderId);
            map.put("orderItems",orderItems);
            //获得用户的分销关系的父id
            Long userPid = getUserPid(order.getUserId());
            map.put("userPid",userPid);
            //微信提醒
            log.info("微信提醒-----start");
            String[] param = new String[]{order.getOrderCode(),order.getPayAmount()+"","微信支付"};
            WxSFNoticeUtils.getInstance().orderCreateNotice(comUser,param);
            log.info("微信提醒-----end");
        }catch (Exception e){
            throw new BusinessException(e);
        }
        return map;
    }
    /**
     * 获得订单的收货地址
     * @author hanzengzhi
     * @date 2016/4/10 14:03
     */
    private SfOrderConsignee getOrdConByOrdId(Long orderId){
        return ordConService.getOrdConByOrdId(orderId);
    }
    /**
     * 根据订单id获得订单信息
     * @author hanzengzhi
     * @date 2016/4/10 14:10
     */
    private SfOrder getOrderById(Long orderId){
        return ordService.getOrderById(orderId);
    }
    /**
     * 根据订单id获得订单的详情信息
     * @author hanzengzhi
     * @date 2016/4/10 14:18
     */
    private List<SfOrderItem> getOrderItem(Long orderId){
        return ordItemService.getOrderItemByOrderId(orderId);
    }

    /**
     * 获得购买人的分销关系
     * @author hanzengzhi
     * @date 2016/4/14 16:29
     */
    private Long getUserPid(Long userId){
        SfUserRelation userRelation = sfUserRelationService.getSfUserRelationByUserId(userId);
        if (userRelation==null){
            throw new BusinessException("支付成功后获得分销关系为null");
        }else{
           return userRelation.getUserPid();
        }
    }

    public void addSfOrderPayment(SfOrderPayment payment) {
        paymentMapper.insert(payment);
    }

    /**
     * 调用微信支付
     * @author hanzengzhi
     * @date 2016/4/11 10:40
     */
    public WxPaySysParamReq callWechatPay(HttpServletRequest request, String orderCode, Long orderId) {
        WxPaySysParamReq wpspr = new WxPaySysParamReq();
        SfOrder order = ordService.getOrderById(orderId);
        if (order!=null&&order.getOrderStatus().equals(0)){
            wpspr.setOrderId(orderCode);
            wpspr.setSignType("MD5");
            wpspr.setNonceStr(WXBeanUtils.createGenerateStr());
            String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
            wpspr.setSuccessUrl(basePath + "orderPay/paySuccessCallBack.html?orderId="+orderId);
            wpspr.setSign(WXBeanUtils.toSignString(wpspr));
        }else{
            log.info("调用微信支付失败:订单为null或者订单状态不是未支付状态");
            throw new BusinessException("调用微信支付失败:订单为null或者订单状态不是未支付状态");
        }
        return wpspr;
    }

    public SfOrderPayment findOrderPaymentBySerialNum(String paySerialNum) {
        return paymentMapper.selectBySerialNum(paySerialNum);
    }
}
