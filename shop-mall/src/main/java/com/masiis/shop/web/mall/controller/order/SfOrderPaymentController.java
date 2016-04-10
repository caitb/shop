package com.masiis.shop.web.mall.controller.order;

import com.masiis.shop.web.mall.service.order.SfOrderPaymentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by hzz on 2016/4/10.
 *  订单支付
 */
@Controller
@RequestMapping(value = "orderPay.do")
public class SfOrderPaymentController {

    @Resource
    private SfOrderPaymentService orderPaymentService;
    /**
     * 支付成功回调
     * @author hanzengzhi
     * @date 2016/4/10 13:56
     */
    @RequestMapping(value = "paySuccessCallBack.html")
   public String paySuccessCallBack(HttpServletRequest request, HttpServletResponse response,
                                    @RequestParam(value = "orderId", required = true) Long orderId,
                                    Model model){
       Map<String,Object> map = orderPaymentService.paySuccessCallBack(orderId);
       model.addAttribute("orderConsignee",map.get("orderConsignee"));
       model.addAttribute("order",map.get("order"));
       model.addAttribute("orderItems",map.get("orderItems"));
        return "mall/order/zhifuchenggong";
   }
}
