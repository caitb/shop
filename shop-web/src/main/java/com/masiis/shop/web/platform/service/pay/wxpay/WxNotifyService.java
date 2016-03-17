package com.masiis.shop.web.platform.service.pay.wxpay;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.po.PfBorderPayment;
import com.masiis.shop.web.platform.beans.pay.wxpay.CallBackNotifyReq;
import com.masiis.shop.web.platform.service.order.BOrderService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.swing.border.Border;

/**
 * Created by lzh on 2016/3/16.
 */
@Service
public class WxNotifyService {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private BOrderService bOrderService;

    /**
     * 处理微信支付订单异步回调业务
     *
     * @param param
     */
    public void handleWxPayNotify(CallBackNotifyReq param) {
        // 支付流水号
        String paySerialNum = param.getOut_trade_no();
        String orderType = String.valueOf(paySerialNum.charAt(0));
        if("B".equals(orderType)){
            try {
                PfBorderPayment payment = bOrderService.findOrderPaymentBySerialNum(paySerialNum);
                if(payment == null){
                    throw new BusinessException("该支付流水号不存在,pay_serial_num:" + paySerialNum);
                }
                if(payment.getIsEnabled() == 1){
                    // 已经支付
                    log.info("该订单已经支付,支付流水号:" + paySerialNum);
                    return;
                }
                // 调用borderService的方法处理
                bOrderService.payBOrder(payment, param.getTransaction_id());
            } catch (Exception e) {
                // 判断异常类型
                if(e instanceof BusinessException && "".equals(e.getMessage())){
                    // 如果是订单已支付且缺货,走次流程
                    String err = "订单已支付且缺货,系统支付流水号:" + paySerialNum + "||" + e.getMessage();
                    log.error(err);
                    throw new BusinessException(err);
                } else {
                    log.error("订单支付成功处理失败,系统支付流水号:" + paySerialNum, e);
                    // 普通支付处理失败流程

                }
            }
        } else {
            throw new BusinessException("支付流水号类型错误!");
        }
    }
}
