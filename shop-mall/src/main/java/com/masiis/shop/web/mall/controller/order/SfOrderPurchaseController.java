package com.masiis.shop.web.mall.controller.order;

import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.web.mall.controller.base.BaseController;
import com.masiis.shop.web.mall.service.order.SfOrderPurchaseService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by hzz on 2016/4/8.
 */
@Controller
@RequestMapping("orderPurchase")
public class SfOrderPurchaseController extends BaseController {

    @Resource
    private SfOrderPurchaseService sfOrderPurchaseService;

    /**
     * 获得确认订单界面，地址信息和商品信息
     * @author hanzengzhi
     * @date 2016/4/8 20:45
     */
    @RequestMapping(value = "getShopCartInfo.html")
    public String getConfirmOrderInfoController(HttpServletRequest request, HttpServletResponse response,
                                                @RequestParam(value = "sfShopId") Long sfShopId,
                                                @RequestParam(value = "selectedAddressId", required = false) Long selectedAddressId){
        ComUser comUser = getComUser(request);
        sfOrderPurchaseService.getConfirmOrderInfo(comUser.getId(),selectedAddressId,sfShopId);
        return null;
    }
    /**
     * 提交订单
     * @author hanzengzhi
     * @date 2016/4/10 11:09
     */
    @RequestMapping(value = "submitOrder.do")
    public String submitOrder(HttpServletRequest request,HttpServletResponse response,
                              @RequestParam(value = "addressId" , required = true)Long addressId,
                              @RequestParam(value = "message" , required = false)String message,
                              @RequestParam(value = "sfShopId",required = true) Long sfShopId,
                              @RequestParam(value = "selectedAddressId", required = true) Long selectedAddressId){
        ComUser comUser = getComUser(request);
        sfOrderPurchaseService.submitOrder(comUser.getId(),selectedAddressId,sfShopId,message);
        return null;
    }
}
