package com.masiis.shop.web.mall.controller.order;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.web.mall.controller.base.BaseController;
import com.masiis.shop.web.mall.service.order.SfOrderPurchaseService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by hzz on 2016/4/8.
 */
@Controller
@RequestMapping("orderPurchase")
public class SfOrderPurchaseController extends BaseController {

    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private SfOrderPurchaseService sfOrderPurchaseService;


    /**
     * 获得确认订单界面，地址信息和商品信息
     * @author hanzengzhi
     * @date 2016/4/8 20:45
     */
    @RequestMapping(value = "getShopCartInfo.html")
    public String getConfirmOrderInfoController(HttpServletRequest request, HttpServletResponse response,
                                                @RequestParam(value = "shopId") Long shopId,
                                                @RequestParam(value = "selectedAddressId", required = false) Long selectedAddressId,
                                                Model model){
        ComUser comUser = getComUser(request);
        Map<String,Object> map = sfOrderPurchaseService.getConfirmOrderInfo(comUser.getId(),selectedAddressId,shopId);
        model.addAttribute("shopId",shopId);
        model.addAttribute("comUserAddress",map.get("comUserAddress"));
        model.addAttribute("shopCartSkuDetails",map.get("shopCartSkuDetails"));
        model.addAttribute("skuTotalPrice",map.get("skuTotalPrice"));
        model.addAttribute("skuTotalShipAmount",map.get("skuTotalShipAmount"));
        model.addAttribute("totalQuantity",map.get("totalQuantity"));
        model.addAttribute("totalPrice",map.get("totalPrice"));
        return "mall/order/tijiaodingdan";
    }
    /**
     * 提交订单
     * @author hanzengzhi
     * @date 2016/4/10 11:09
     */
    @ResponseBody
    @RequestMapping(value = "submitOrder.do")
    public String submitOrder(HttpServletRequest request,HttpServletResponse response,
                              @RequestParam(value = "message" , required = false)String message,
                              @RequestParam(value = "shopId",required = true) Long sfShopId,
                              @RequestParam(value = "selectedAddressId", required = true) Long selectedAddressId){
        JSONObject obj = new JSONObject();
        ComUser comUser = getComUser(request);
        Long orderId = sfOrderPurchaseService.submitOrder(comUser.getId(),selectedAddressId,sfShopId,message);
        if (orderId != null){
            obj.put("isSubmitOrder","true");
        }else{
            obj.put("isSubmitOrder","false");
        }
        obj.put("sfOrderId",orderId);
        return obj.toJSONString();
    }
}
