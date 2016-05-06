package com.masiis.shop.web.event.wx.controller;

import com.masiis.shop.common.util.HttpClientUtils;
import com.masiis.shop.common.util.SHAUtils;
import com.masiis.shop.web.event.wx.bean.WxArticleRes;
import com.masiis.shop.web.event.wx.bean.WxEventBody;
import com.masiis.shop.web.event.wx.bean.WxEventCheck;
import com.masiis.shop.web.event.wx.service.WxEventService;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.utils.WXBeanUtils;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

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

    @Resource
    private WxEventService wxEventService;

    @RequestMapping("/check")
    @ResponseBody
    public String receiveWxEvent(HttpServletRequest request, WxEventCheck req) throws IOException {
        System.out.println(req.toString());
        try {
            if (StringUtils.isBlank(req.getTimestamp())
                    || StringUtils.isBlank(req.getNonce())
                    || StringUtils.isBlank(req.getSignature())) {
                return "fail";
            }

            // 修改配置校验
            String[] ws = new String[3];
            ws[0] = req.getNonce();
            ws[1] = req.getTimestamp();
            ws[2] = token;
            Arrays.sort(ws, String.CASE_INSENSITIVE_ORDER);

            String sign = SHAUtils.encodeSHA1((ws[0] + ws[1] + ws[2]).getBytes("UTF-8")).toLowerCase();
            if (!req.getSignature().equals(sign)) {
                return "fail";
            }

            if (StringUtils.isNotBlank(req.getEchostr())) {
                return req.getEchostr();
            }

            // 解析消息参数
            String requestBody = getRequestBody(request);
            System.out.println(requestBody);
            XStream xStream = new XStream(new DomDriver("UTF-8"));
            xStream.processAnnotations(WxEventBody.class);
            WxEventBody body = (WxEventBody) xStream.fromXML(requestBody);

            // 分事件处理
            switch (body.getMsgType()) {
                case "event":
                    // 事件推送
                    WxArticleRes res = wxEventService.handleEvent(body);
                    xStream.processAnnotations(WxArticleRes.class);
                    String resStr = xStream.toXML(res);
                    System.out.println(resStr);
                    return resStr;
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return "fail";
    }

    public static void main(String... args) throws UnsupportedEncodingException {
        /*String xml = "<xml><ToUserName><![CDATA[gh_150ec2776586]]></ToUserName>\n" +
                "<FromUserName><![CDATA[o6wZ2s2y3DkS4RBZ_7G0R0bLvWIY]]></FromUserName>\n" +
                "<CreateTime>1462521876</CreateTime>\n" +
                "<MsgType><![CDATA[event]]></MsgType>\n" +
                "<Event><![CDATA[SCAN]]></Event>\n" +
                "<EventKey><![CDATA[89]]></EventKey>\n" +
                "<Ticket><![CDATA[gQFn7zoAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL1kwaWFrajdsNWZHY1RzbWV0MlF2AAIEQE0sVwMEAAAAAA==]]></Ticket>\n" +
                "</xml>";
        XStream xStream = new XStream(new DomDriver("UTF-8"));
        xStream.processAnnotations(WxEventBody.class);
        WxEventBody body = (WxEventBody) xStream.fromXML(xml);
        System.out.println(body);
        String res = xStream.toXML(body);
        System.out.println(res);*/

        WxEventBody reqBody = new WxEventBody();
        reqBody.setToUserName("gh_150ec2776586");
        reqBody.setFromUserName("o6wZ2s2y3DkS4RBZ_7G0R0bLvWIY");
        reqBody.setCreateTime(1462521876l);
        reqBody.setEvent("SCAN");
        reqBody.setMsgType("event");
        reqBody.setEventKey("622");
        reqBody.setTicket("gQFn7zoAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL1kwaWFrajdsNWZHY1RzbWV0MlF2AAIEQE0sVwMEAAAAAA==");
        XStream xStream = new XStream(new DomDriver("UTF-8"));
        xStream.processAnnotations(WxEventBody.class);
        String res = HttpClientUtils.httpPost("http://localhost:8080/wxevent/check?signature=ec0fa853ecb7270b4db4a6372431901a18cfec3e&timestamp=1462521876&nonce=833666333",
                xStream.toXML(reqBody));
        System.out.println(res);
    }
}
