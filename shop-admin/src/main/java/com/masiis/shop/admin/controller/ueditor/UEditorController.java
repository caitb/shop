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
 *
 * Created by cai_tb on 16/3/3.
 */
@Controller
@RequestMapping("/")
public class UEditorController {

    @RequestMapping("/ueditor.do")
    public String ueditor(HttpServletRequest request, HttpServletResponse response){
        String action = request.getParameter("action");

        if("config".equals(action)){
            return "forward:/config.json";
        }
        if("uploadimage".equals(action)){
            return "forward:/uploadimage";
        }
        return null;
    }

    @RequestMapping("/config.json")
    @ResponseBody
    public Object config(HttpServletRequest request, HttpServletResponse response){

        response.setHeader("Content-Type" , "text/html");
        String rootPath = request.getSession().getServletContext().getRealPath( "/" );
        String configJson = new ActionEnter( request, rootPath ).exec();

        return configJson;
    }

    /**
     * 上传图片
     * @param request
     * @param response
     * @param files
     * @return
     */
    @RequestMapping("/uploadimage")
    @ResponseBody
    public Object uploadimage(HttpServletRequest request, HttpServletResponse response,
                              @RequestParam("upfile")MultipartFile[] files
    ) {
        if (files != null && files.length > 0) {
            for (int i = 0; i < files.length; i++) {
                MultipartFile file = files[i];
                // 保存文件
                saveFile(request, file);
            }
        }

        response.setHeader("Content-Type" , "text/html");
        String rootPath = request.getSession().getServletContext().getRealPath( "/" );
        String configJson = new ActionEnter( request, rootPath ).exec();

        return configJson;
    }

    private boolean saveFile(HttpServletRequest request, MultipartFile file) {
        // 判断文件是否为空
        if (!file.isEmpty()) {
            try {
                // 保存的文件路径(如果用的是Tomcat服务器，文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\upload\\文件夹中  )
                String filePath = request.getSession().getServletContext()
                        .getRealPath("/") + "upload/" + file.getOriginalFilename();
                File saveDir = new File(filePath);
                if (!saveDir.getParentFile().exists())
                    saveDir.getParentFile().mkdirs();

                // 转存文件
                file.transferTo(saveDir);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
