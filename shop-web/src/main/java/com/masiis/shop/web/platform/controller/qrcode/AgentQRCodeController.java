package com.masiis.shop.web.platform.controller.qrcode;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.qrcode.WeiXinQRCodeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 代理商二维码
 * Created by cai_tb on 16/5/6.
 */
@Controller
@RequestMapping("/qrcode")
public class AgentQRCodeController extends BaseController {

    private final static Log log = LogFactory.getLog(AgentQRCodeController.class);

    @Resource
    private WeiXinQRCodeService weiXinQRCodeService;

    @RequestMapping("/test")
    public String test(){
        return "platform/qrcode";
    }

    @RequestMapping("/agent/create")
    @ResponseBody
    public String createQRCode(HttpServletRequest request, HttpServletResponse response){
        ComUser comUser = null;
        try {
            comUser = getComUser(request);

            String qrcodeUrl = weiXinQRCodeService.createAgentQRCode(663);

            return qrcodeUrl;
        } catch (Exception e) {
            log.error("获取代理商二维码失败!");
            e.printStackTrace();
        }

        return "";
    }
}
