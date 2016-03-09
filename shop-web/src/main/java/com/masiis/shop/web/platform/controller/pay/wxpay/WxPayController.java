package com.masiis.shop.web.platform.controller.pay.wxpay;

import com.fasterxml.jackson.databind.deser.Deserializers;
import com.masiis.shop.web.platform.controller.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lzh on 2016/3/9.
 */
@Controller
@RequestMapping("/wxpay")
public class WxPayController extends BaseController{

    @RequestMapping("/wtpay")
    public String wxpayPage(HttpServletRequest request, String code, String state){
        System.out.println(getIpAddr(request));
        return "pay/wxpay/wxpayPage";
    }
}
