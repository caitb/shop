package com.masiis.shop.admin.controller.ueditor;

import com.baidu.ueditor.ActionEnter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 百度富文本编辑器后端配置加载类
 *
 * Created by cai_tb on 16/3/3.
 */
@Controller
@RequestMapping("/")
public class ConfigController {

    @RequestMapping("/config.json")
    @ResponseBody
    public Object config(HttpServletRequest request, HttpServletResponse response){
        response.setHeader("Content-Type" , "text/html");

        String rootPath = request.getSession().getServletContext().getRealPath( "/" );

        String configJson = new ActionEnter( request, rootPath ).exec();
        return configJson;
    }
}
