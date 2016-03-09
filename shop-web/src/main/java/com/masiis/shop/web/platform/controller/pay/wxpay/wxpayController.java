package com.masiis.shop.web.platform.controller.pay.wxpay;

import com.masiis.shop.web.platform.controller.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lzh on 2016/3/8.
 */
@RequestMapping("/wxpay")
@Controller
public class wxpayController extends BaseController{

    @RequestMapping("/wtPay")
    public String wxpayPage(){

        return "pay/wxpay/wxpayPage";
    }

}
