package com.masiis.shop.web.mall.service.order;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.mall.order.SfOrderPaymentMapper;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.mall.beans.pay.wxpay.WxPaySysParamReq;
import com.masiis.shop.web.mall.utils.WXBeanUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
            int ii = updateOrder(order);
            if (ii == 1){
                log.info("更新订单成功----end");
            }else{
                log.info("更新订单失败----end");
                throw new BusinessException("更新订单失败----end");
            }
            //更新订单操作日志
            log.info("更新订单操作日志---start");
            SfOrderOperationLog ordOperLog = ordOperLogService.getOrdOperLogByOrderId(order.getId());
            int iii = updateOrdOperLog(ordOperLog);
            if (iii ==1 ){
                log.info("更新订单操作日志成功----end");
            }else{
                log.info("更新订单操作日志失败----end");
                throw new BusinessException("更新订单操作日志失败----end");
            }
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
    private int updateOrder(SfOrder order){
        order.setModifyTime(new Date());
        order.setPayTime(new Date());
        order.setOrderStatus(7);//待发货
        order.setPayStatus(1);//已支付
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
     * 支付成功回调
     * @author hanzengzhi
     * @date 2016/4/10 13:59
     */
    @Transactional(propagation = Propagation.REQUIRED,readOnly = true)
    public Map<String,Object> paySuccessCallBack(Long orderId){
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
        wpspr.setOrderId(orderCode);
        wpspr.setSignType("MD5");
        wpspr.setNonceStr(WXBeanUtils.createGenerateStr());
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
        wpspr.setSuccessUrl(basePath + "orderPay/paySuccessCallBack.html?orderId="+orderId);
        wpspr.setSign(WXBeanUtils.toSignString(wpspr));
        return wpspr;
    }

    public SfOrderPayment findOrderPaymentBySerialNum(String paySerialNum) {
        return paymentMapper.selectBySerialNum(paySerialNum);
    }
}
