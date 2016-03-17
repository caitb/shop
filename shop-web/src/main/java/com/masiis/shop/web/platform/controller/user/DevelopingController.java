package com.masiis.shop.web.platform.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 发展合伙人
 * Created by cai_tb on 16/3/17.
 */
@Controller
@RequestMapping("/developing")
public class DevelopingController {

    @RequestMapping("/ui")
    public ModelAndView ui(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mav = new ModelAndView("platform/user/developing");

        return mav;
    }

}
