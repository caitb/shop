package com.masiis.shop.admin.controller.operate;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by cai_tb on 16/4/1.
 */
@Controller
@RequestMapping("/operate/advert")
public class AdvertController {

    @RequestMapping("/list.shtml")
    public String list(){
        return "operate/advert/list";
    }

    @RequestMapping("/list.do")
    @ResponseBody
    public Object list(HttpServletRequest request, HttpServletResponse response,
                       Integer pageNumber,
                       Integer pageSize,
                       String sortOrder){

        return null;
    }
}
