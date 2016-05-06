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
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;

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
    public String receiveWxEvent(HttpServletRequest request, WxEventCheck req) throws IOException {
        System.out.println(req.toString());

        /*if(StringUtils.isBlank(req.getTimestamp())
                || StringUtils.isBlank(req.getEchostr())
                || StringUtils.isBlank(req.getNonce())
                || StringUtils.isBlank(req.getSignature())){
            return "fail";
        }
        String[] ws = new String[3];
        ws[0] = req.getNonce();
        ws[1] = req.getTimestamp();
        ws[2] = token;
        Arrays.sort(ws, String.CASE_INSENSITIVE_ORDER);
        String sign = SHAUtils.encodeSHA1((ws[0] + ws[1] + ws[2]).getBytes("UTF-8")).toLowerCase();
        if(!req.getSignature().equals(sign)){
            return "fail";
        }*/

        String requestBody = getRequestBody(request);
        System.out.println(requestBody);


        return req.getEchostr();
    }

    public static void main(String... args) throws UnsupportedEncodingException {
        String[] ws = new String[3];
        ws[0] = "1462514180";
        ws[1] = "443041202";
        ws[2] = token;
        Arrays.sort(ws, String.CASE_INSENSITIVE_ORDER);

        /*String sign = SHAUtils.encodeSHA1((ws[0] + ws[1] + ws[2]).getBytes("UTF-8")).toLowerCase();
        System.out.println(sign);
        System.out.println("f3a9c71cb002192bd186414565dd82b2875505b2".equals(sign));*/
        System.out.println(new Date().getTime());
    }
}
