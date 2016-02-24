package com.masiis.shop.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.util.HttpClientUtils;
import com.masiis.shop.web.weixin.bean.AccessTokenRes;
import com.masiis.shop.web.weixin.bean.ErrorRes;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by lzh on 2016/2/23.
 */
@Controller
@RequestMapping("/verify")
public class VerifyController {

    @RequestMapping("/actk")
    public String getCode(HttpServletRequest request, HttpServletResponse response,
                        String code, String state){
        if(StringUtils.isBlank(code)){
            request.setAttribute("state", state);
            return "";
        }
        if(StringUtils.isBlank(state)){
            return "";
        }
        // 获取access_token
        System.out.println("开始获取access_token...");
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?"
                        + "appid=wx7c874d5a102dccef&"
                        + "secret=67f21b9a5df948c9dbf203393d8fb1a1&"
                        + "code=" + code + "&"
                        + "grant_type=authorization_code";
        String result = HttpClientUtils.httpGet(url);
        System.out.println("result:" + result);
        AccessTokenRes res = JSONObject.parseObject(result, AccessTokenRes.class);
        ErrorRes resErr = JSONObject.parseObject(result, ErrorRes.class);
        if(StringUtils.isBlank(resErr.getErrcode())){
            request.getSession().setAttribute("login", "login");
            try {
                return "redirect:" + URLDecoder.decode(state, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return "index";
            }
        }else{
            // 请求失败

        }

        return "redirect:/";
    }
}
