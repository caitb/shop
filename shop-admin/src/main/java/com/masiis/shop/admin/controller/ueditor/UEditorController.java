package com.masiis.shop.admin.controller.ueditor;

import com.baidu.ueditor.ActionEnter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * 百度富文本编辑器接口类
 * <p/>
 * Created by cai_tb on 16/3/3.
 */
@Controller
@RequestMapping("/")
public class UEditorController {

    @RequestMapping("/ueditor.do")
    @ResponseBody
    public String ueditor(HttpServletRequest request, HttpServletResponse response) {

        response.setHeader("Content-Type", "text/html");
        String rootPath = request.getSession().getServletContext().getRealPath("/");
        String configJson = new ActionEnter(request, rootPath).exec();

        return configJson;
    }

}
