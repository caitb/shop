package com.masiis.shop.web.mall.controller.order;

/**
 * Created by hzz on 2016/4/11.
 */

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.dao.po.SfOrder;
import com.masiis.shop.common.beans.wx.wxpay.WxPaySysParamReq;
import com.masiis.shop.common.constant.mall.SysConstants;
import com.masiis.shop.web.mall.controller.base.BaseController;
import com.masiis.shop.web.mall.service.order.SfOrderPayService;
import com.masiis.shop.web.mall.service.shop.SfShopService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by hzz on 2016/4/10.
 *  订单支付
 */
@Controller
@RequestMapping(value = "orderPay")
public class SfOrderPayController extends BaseController {

    private Logger log = Logger.getLogger(this.getClass());
    @Resource
    private SfOrderPayService orderPayService;


    /**
     * 获得需要支付的订单的信息
     * @author hanzengzhi
     * @date 2016/4/11 19:39
     */
    @RequestMapping(value = "getOrderInfo.html")
    public String getOrderInfo(HttpServletRequest request ,HttpServletResponse response,
                               @RequestParam(value = "orderId",required = true)Long orderId,
                               Model model)throws Exception{
        Map<String,Object>  map =  orderPayService.getOrderInfo(orderId);
        SfOrder order = (SfOrder) map.get("order");
        WxPaySysParamReq wpspr = orderPayService.callWechatPay(request, order.getOrderCode(), orderId,false);

        model.addAttribute("order", order);
        model.addAttribute("orderItems", map.get("orderItems"));
        model.addAttribute("paramReq", URLEncoder.encode(JSONObject.toJSONString(wpspr), "UTF-8"));
        return "mall/order/zhifudingdan";
    }

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
        WxPaySysParamReq wpspr = orderPayService.callWechatPay(request, orderCode,orderId,true);
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
                                     Model model)throws Exception{
        Map<String,Object> map = orderPayService.paySuccessCallBack(orderId);
        model.addAttribute("orderConsignee",map.get("orderConsignee"));
        model.addAttribute("order",map.get("order"));
        log.info("userPid--------------------"+request.getSession().getAttribute("userPid"));
        model.addAttribute("userPid",request.getSession().getAttribute("userPid"));
        model.addAttribute("mallDomainNameAddress", SysConstants.MALL_DOMAIN_NAME_ADDRESS);
        model.addAttribute("isTurnTableRule",map.get("isTurnTableRule"));
        model.addAttribute("turnTableRuleTimes",map.get("turnTableRuleTimes"));
        return "mall/order/paySuccess";
    }

    /**
     * 获得订单详情
     * @author hanzengzhi
     * @date 2016/4/10 13:56
     */
    @RequestMapping(value = "getOrderDetail.html")
    public String getOrderDetail(HttpServletRequest request, HttpServletResponse response,
                                     @RequestParam(value = "orderId", required = true) Long orderId,
                                     Model model)throws Exception{
        Map<String,Object> map = orderPayService.getOrderDetail(orderId);
        model.addAttribute("orderConsignee", map.get("orderConsignee"));
        model.addAttribute("order",map.get("order"));
        model.addAttribute("isFreeShipAmount",map.get("isFreeShipAmount"));
        model.addAttribute("skuTotalShipAmount",map.get("skuTotalShipAmount"));
        model.addAttribute("orderItems",map.get("orderItems"));
        model.addAttribute("userPid",map.get("userPid"));
        model.addAttribute("mallDomainNameAddress", SysConstants.MALL_DOMAIN_NAME_ADDRESS);
        model.addAttribute("wxQrCode",map.get("wxQrCode"));
        return "mall/order/orderDetail";
    }

}
