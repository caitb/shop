package com.masiis.shop.web.mall.controller.order;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by hzz on 2016/4/10.
 *  订单支付
 */
@Controller
@RequestMapping(value = "orderPay.do")
public class SfOrderPaymentController {

    /**
     * 支付成功回调
     * @author hanzengzhi
     * @date 2016/4/10 13:56
     */
   public String paySuccessCallBack(HttpServletRequest request, HttpServletResponse response){

       return null;
   }
}
