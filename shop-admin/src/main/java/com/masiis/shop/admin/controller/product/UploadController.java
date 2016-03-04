package com.masiis.shop.admin.controller.product;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by cai_tb on 16/3/4.
 */
@Controller
@RequestMapping("/upload")
public class UploadController {

    @RequestMapping("/uploadImage.do")
    public String save(HttpServletRequest request, HttpServletResponse response){

        return null;
    }
}
