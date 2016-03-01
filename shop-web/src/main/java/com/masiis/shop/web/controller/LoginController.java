package com.masiis.shop.web.controller;

import com.fasterxml.jackson.databind.deser.Deserializers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lzh on 2016/2/27.
 */
@Controller
@RequestMapping("/lo")
public class LoginController extends BaseController{
    /**
     *
     *@return
     */
    @RequestMapping("/index")
    public String toIndex(){
        return "index";
    }
    /**
     * 商品详情
     * @return
     */
    @RequestMapping("/detail")
    public String toDetail(){
        return "platform/details";
    }

    /**
     * 申请合伙人
     * @return
     */
    @RequestMapping("/quote")
    public String partnerRegister(){
        return "platform/ptnerQuote";
    }

    /**
     * 提交申请
     * @return
     */
    @RequestMapping("commitApplication")
    public  String toCommitApplication(){
        return "platform/commitApplication";
    }

    /**
     * 默认地址
     * @return
     */
    @RequestMapping("defaultAddress")
    public  String toDefaultAddress(){
        return "platform/defaultAddress";
    }

    /**
     * 使用页面
     * @return
     */
    @RequestMapping("employ")
    public  String toEmploy(){
        return "platform/employ";
    }

    /**
     * 支付
     * @return
     */
    @RequestMapping("payment")
    public  String toPayment(){
        return "platform/payment";
    }

    /**
     * 注册
     * @return
     */
    @RequestMapping("registration")
    public  String toRegistration(){
        return "platform/registration";
    }

    /**
     * 注册第二步
     * @return
     */
    @RequestMapping("registration-t")
    public  String toRegistrationT(){
        return "platform/registration-T";
    }
}
