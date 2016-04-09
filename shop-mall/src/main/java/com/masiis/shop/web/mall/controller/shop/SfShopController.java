package com.masiis.shop.web.mall.controller.shop;

import com.masiis.shop.web.mall.service.shop.SfShopService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Date:2016/4/7
 * @auth:lzh
 */
@RequestMapping("/shop")
public class SfShopController {

    @Resource
    private SfShopService sfShopService;

    @RequestMapping("/shout")
    @ResponseBody
    public Object shout(HttpServletRequest request, HttpServletResponse response, Long shopId){

        return null;
    }
}
