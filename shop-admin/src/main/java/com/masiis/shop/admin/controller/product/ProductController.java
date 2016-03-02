package com.masiis.shop.admin.controller.product;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ZhaoLiang on 2016/3/2.
 */
@Controller
@RequestMapping("/product")
public class ProductController {

    @RequestMapping("/add.shtml")
    public String add(HttpServletRequest request, HttpServletResponse response){
        return "product/add";
    }
}
