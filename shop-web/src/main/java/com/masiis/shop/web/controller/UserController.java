package com.masiis.shop.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by cai_tb on 16/2/16.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping(value = "/login",produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String login(HttpServletRequest request, HttpServletResponse response){

        return "";
    }
}
