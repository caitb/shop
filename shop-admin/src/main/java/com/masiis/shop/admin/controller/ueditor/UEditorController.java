package com.masiis.shop.admin.controller.ueditor;

import com.baidu.ueditor.ActionEnter;
import com.masiis.shop.common.util.OSSObjectUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;

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
    public String ueditor(HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {

        response.setHeader("Content-Type", "text/html");
        String rootPath = request.getSession().getServletContext().getRealPath("/");
        String configJson = new ActionEnter(request, rootPath).exec();

        /*为了在富文本选择图片时能够马上显示,加入一下代码直接上传到OSS图片服务器*/
//        String action = request.getParameter("action");
//        if("uploadimage".equals(action)){
//            /* 上传商品详情图 */
//            String realPath = request.getServletContext().getRealPath("/");
//            File detailDir = new File(realPath + "static/product/detail_img");
//            if(detailDir.exists() && detailDir.listFiles().length > 0){
//                File[] files = detailDir.listFiles();
//                for(File f : files){
//                    OSSObjectUtils.uploadFile("mmshop", f, "static/product/detail_img/");
//                    f.delete();
//                }
//            }
//        }

        return configJson;
    }

}
