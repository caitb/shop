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

    @RequestMapping("/index")
    public String toIndex(){
        return "index";
    }

    @RequestMapping("/detail")
    public String toDetail(){
        return "platform/details";
    }

    @RequestMapping("/quote")
    public String partnerRegister(){
        return "platform/ptnerQuote";
    }
}
