package com.masiis.shop.admin.controller.ueditor;

import com.alibaba.druid.support.json.JSONParser;
import com.alibaba.druid.support.json.JSONUtils;
import com.baidu.ueditor.ActionEnter;
import com.masiis.shop.common.util.ImageUtils;
import com.masiis.shop.common.util.OSSObjectUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

/**
 * 百度富文本编辑器接口类
 * <p/>
 * Created by cai_tb on 16/3/3.
 */
@Controller
@RequestMapping("/")
public class UEditorController {

    private final static int[] scaleArr = {220, 308, 800};

    @RequestMapping("/ueditor.do")
    @ResponseBody
    public String ueditor(HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {

        response.setHeader("Content-Type", "text/html");
        String rootPath = request.getSession().getServletContext().getRealPath("/");
        String configJson = new ActionEnter(request, rootPath).exec();

        //如果本次操作是上传图片,进行后续处理
        String action = request.getParameter("action");
        if(action != null && "uploadimage".equals(action)){
            uploadToOSS(request, configJson);
        }

        return configJson;
    }

    public void uploadToOSS(HttpServletRequest request, String configJson) throws FileNotFoundException {
        Map<String, Object> configMap = new JSONParser(configJson).parseMap();
        String state = (String)configMap.get("state");

        //如果成功上传了图片,将图片转存到oss
        if(state != null  && "SUCCESS".equals(state)){

            String rootPath = request.getServletContext().getRealPath("/");
            String scale = request.getParameter("scale");//缩放
            String osspath = request.getParameter("osspath");//oss上传路径
                   osspath = osspath==null ? "static/product/detail_img/" : osspath;
            String imgPath = rootPath.substring(0, rootPath.lastIndexOf(File.separator))+configMap.get("url");//本地图片路径

            //上传缩放后的图片到oss
            if(scale != null){
                for(int i=0; i<scaleArr.length; i++){
                    File scaleDir = new File(rootPath+"static/product/detail_img/"+scaleArr[i]+"_"+scaleArr[i]);
                    if(!scaleDir.exists()) scaleDir.mkdirs();
                    ImageUtils.scale2(imgPath, scaleDir.getAbsolutePath()+"/"+configMap.get("title"), scaleArr[i], scaleArr[i], true);
                    File scaleImgFile = new File(scaleDir.getAbsolutePath()+"/"+configMap.get("title"));
                    OSSObjectUtils.uploadFile(scaleImgFile.getAbsoluteFile(), "static/product/"+scaleArr[i]+"_"+scaleArr[i]+"/");
                    scaleImgFile.delete();
                }
            }

            //上传原图到oss
            File imgFile = new File(imgPath);
            OSSObjectUtils.uploadFile(imgFile, osspath);
            imgFile.delete();
        }
    }

}
