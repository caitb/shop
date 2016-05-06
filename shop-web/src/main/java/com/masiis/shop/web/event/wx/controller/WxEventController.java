package com.masiis.shop.web.event.wx.controller;

import com.masiis.shop.common.util.SHAUtils;
import com.masiis.shop.web.event.wx.bean.WxEventCheck;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.utils.WXBeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * @Date 2016/5/6
 * @Auther lzh
 */
@Controller
@RequestMapping("/wxevent")
public class WxEventController extends BaseController {
    private static final String token = "slkd2H45467GG7622HSLsKsdKJHKS97E";
    private static final String encodingAESKey = "lSICsrmNzJMt3BExrOJrq9uBmrLoLNJ9aQEpq6g4Awc";
    private Logger log = Logger.getLogger(this.getClass());

    @RequestMapping("/check")
    @ResponseBody
    public String receiveWxEvent(HttpServletRequest request, WxEventCheck req) throws UnsupportedEncodingException {
        System.out.println(req.toString());

        if(StringUtils.isBlank(req.getTimestamp())
                || StringUtils.isBlank(req.getEchostr())
                || StringUtils.isBlank(req.getNonce())
                || StringUtils.isBlank(req.getSignature())){
            return "fail";
        }
        String ws = req.getNonce() + req.getTimestamp() + token;
        String sign = SHAUtils.encodeSHA1(ws.getBytes("UTF-8")).toLowerCase();
        if(!req.getSignature().equals(sign)){
            return "fail";
        }
        return req.getEchostr();
    }
}
