package com.masiis.shop.web.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 首页
 * Created by cai_tb on 16/2/22.
 */
@Controller
@RequestMapping("/main")
public class MainController {

    @RequestMapping("/index.shtml")
    public String index(){
        return "index";
    }


}
