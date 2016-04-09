package com.masiis.shop.web.mall.controller.order;

import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.web.mall.controller.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by hzz on 2016/4/8.
 */
@Controller
@RequestMapping("orderPurchase")
public class SfOrderPurchaseController extends BaseController {

    /**
     * 获得确认订单界面，地址信息和商品信息
     * @author hanzengzhi
     * @date 2016/4/8 20:45
     */
    @RequestMapping(value = "getShopCartInfo.html")
    public String getConfirmOrderInfoController(HttpServletRequest request, HttpServletResponse response,
                                                @RequestParam(value = "selectedAddressId", required = false) Long selectedAddressId){
        ComUser comUser = getComUser(request);
        return null;
    }
}
