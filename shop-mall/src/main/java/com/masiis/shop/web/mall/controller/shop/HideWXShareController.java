package com.masiis.shop.web.mall.controller.shop;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.web.mall.service.shop.JSSDKService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 隐藏微信分享接口
 * Created by cai_tb on 16/4/21.
 */
@Controller
public class HideWXShareController {

    private final static Log log = LogFactory.getLog(HideWXShareController.class);

    @Resource
    private JSSDKService jssdkService;

    /**
     * 隐藏微信分享接口
     * @param request
     * @param response
     * @param hideUrl  要隐藏微信分享功能的页面的http地址(包括请求参数)
     * @return
     */
    @RequestMapping("/hideWXShare")
    @ResponseBody
    public Map<String, String> hideWXShare(HttpServletRequest request, HttpServletResponse response, String hideUrl){

        try {
            log.info("--开始获取隐藏分享功能的数据");
            Map<String, String> shareMap = jssdkService.requestJSSDKData(hideUrl);
            log.info("--隐藏分享功能的数据[shareMap="+shareMap+"]");

            return shareMap;
        } catch (Exception e) {
            log.error("获取调用微信jssdk数据失败![hideUrl="+hideUrl+"]");
            e.printStackTrace();
        }

        return null;
    }
}
