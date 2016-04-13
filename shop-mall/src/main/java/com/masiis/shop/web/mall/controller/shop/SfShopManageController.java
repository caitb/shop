package com.masiis.shop.web.mall.controller.shop;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by cai_tb on 16/4/13.
 */
@Controller
@RequestMapping("shop/manage")
public class SfShopManageController {

    private final static Log log = LogFactory.getLog(SfShopManageController.class);

    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mav = new ModelAndView("mall/shop/manage/index");

        return mav;
    }
}
