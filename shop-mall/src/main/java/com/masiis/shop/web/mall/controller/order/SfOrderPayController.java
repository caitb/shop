package com.masiis.shop.web.mall.controller.order;

/**
 * Created by hzz on 2016/4/11.
 */

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.web.mall.beans.pay.wxpay.WxPaySysParamReq;
import com.masiis.shop.web.mall.service.order.SfOrderPayService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by hzz on 2016/4/10.
 *  订单支付
 */
@Controller
@RequestMapping(value = "orderPay")
public class SfOrderPayController {
    @Resource
    private SfOrderPayService orderPayService;

    /**
     * 调用微信支付
     * @author hanzengzhi
     * @date 2016/4/11 10:37
     */
    @RequestMapping(value = "callWechatPay.do")
    public String callWechatPay(HttpServletRequest request, HttpServletResponse response,
                                @RequestParam(value = "orderCode",required = true) String orderCode,
                                @RequestParam(value = "orderId",required = true) Long orderId,
                                RedirectAttributes attrs){
        WxPaySysParamReq wpspr = orderPayService.callWechatPay(request, orderCode,orderId);
        attrs.addAttribute("param", JSONObject.toJSONString(wpspr));
        return "redirect:/wxpay/wtpay";
    }
    /**
     * 支付成功回调
     * @author hanzengzhi
     * @date 2016/4/10 13:56
     */
    @RequestMapping(value = "paySuccessCallBack.html")
    public String paySuccessCallBack(HttpServletRequest request, HttpServletResponse response,
                                     @RequestParam(value = "orderId", required = true) Long orderId,
                                     Model model){
        Map<String,Object> map = orderPayService.paySuccessCallBack(orderId);
        model.addAttribute("orderConsignee",map.get("orderConsignee"));
        model.addAttribute("order",map.get("order"));
        model.addAttribute("orderItems",map.get("orderItems"));
        return "mall/order/zhifuchenggong";
    }

}
