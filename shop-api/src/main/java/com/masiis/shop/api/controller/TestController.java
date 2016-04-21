package com.masiis.shop.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wangbingjian on 2016/4/19.
 */
@Controller
@RequestMapping(value = "/test")
public class TestController {

    @RequestMapping(value = "/test")
    @ResponseBody
    public String test(){
        System.out.println("劳斯莱斯劳斯莱斯深蓝色");
        return "success";
    }
}
