package com.masiis.shop.admin.controller.ueditor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by cai_tb on 16/3/2.
 */
@Controller
@RequestMapping("/ueditor")
public class UEditorController {

    @RequestMapping("/controller.do")
    public String controller(HttpServletRequest request, HttpServletResponse response){
        return "product/controller";
    }

}
