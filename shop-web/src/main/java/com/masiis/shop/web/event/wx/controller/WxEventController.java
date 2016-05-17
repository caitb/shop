package com.masiis.shop.web.event.wx.controller;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.util.HttpClientUtils;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.common.util.SHAUtils;
import com.masiis.shop.common.util.WxPropertiesUtils;
import com.masiis.shop.web.event.wx.bean.event.*;
import com.masiis.shop.web.event.wx.bean.menu.Button;
import com.masiis.shop.web.event.wx.bean.menu.Menu;
import com.masiis.shop.web.event.wx.service.WxEventService;
import com.masiis.shop.web.platform.controller.base.BaseController;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Date 2016/5/6
 * @Auther lzh
 */
@Controller
@RequestMapping("/wxevent")
public class WxEventController extends BaseController {
    private static final String token = WxPropertiesUtils.getStringValue("wx.conf.pf.WX_EVENT_RECEIVE_TOKEN");
    private static final String encodingAESKey = WxPropertiesUtils.getStringValue("wx.conf.pf.WX_EVENT_RECEIVE_ENCODINGKEY");
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
            String[] ws = {req.getNonce(), req.getTimestamp(), token};
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
            WxBaseMessage res = null;
            switch (body.getMsgType()) {
                case "event":
                    // 事件推送
                    res = wxEventService.handleEvent(body);
                    break;
                default:
                    break;
            }
            if(res == null){
                return "success";
            }
            String resStr = toXML(xStream, res);
            System.out.println(resStr);
            return resStr;

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return "fail";
    }

    public static void main(String... args) throws UnsupportedEncodingException {
        /*WxEventBody reqBody = new WxEventBody();
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
        xStream.processAnnotations(WxBaseEvent.class);
        System.out.println(res);
        Object obj = xStream.fromXML(res);
        System.out.println(obj.getClass().getName());*/

        String createMenu = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=4bQM5R1JabYGZyE9W7p3r0WHGNLunf9-b9LgrhOpthVg1Sdg6_spCMdcOe6r-ar0-y6W_Rg_G8dnvB8fMflJcUPTzgPCQRc1LNjlI6pQv1sr9kg9IUQ4xKuhTLx1cdEDKOIfAAAVZT";
        Menu menu = new Menu();
        List<Button> buttons = new ArrayList<>();
        List<Button> sub_button1 = new ArrayList<>();
        List<Button> sub_button2 = new ArrayList<>();
        sub_button1.add(new Button("关于我们", "view", "http://mp.weixin.qq.com/s?__biz=MzAxMDg1NjY4Mw==&mid=100000035&idx=1&sn=8d77d86ffb986436b94cd3dfb6a08037&scene=23&srcid=0420B5a64GJHrlchm8mdF2O2#wechat_redirect"));
        sub_button1.add(new Button("新闻报道", "view", "http://invest.china.com.cn/html/2016/zhuantibaodao_0108/46155.html"));
        Button b2 = new Button("使用帮助", "view", "http://invest.china.com.cn/html/2016/zhuantibaodao_0108/46155.html");
        //b2.setMedia_id("OaR_DFqGlj6npHbKS8AMfZr2Wjc2dG4KMkHDGIHI_54");
        sub_button1.add(b2);
        Button b1 = new Button("联系我们", "click", null);
        b1.setKey("menu_click_event_contact_us");
        sub_button1.add(b1);

        sub_button2.add(new Button("店铺推广", "view", PropertiesUtils.getStringValue("web.domain.name.address") + "/menu/pmnshop"));
        sub_button2.add(new Button("发展合伙人", "view", PropertiesUtils.getStringValue("web.domain.name.address") + "/menu/pmnpartner"));

        buttons.add(new Button("关于麦链", sub_button1));
        buttons.add(new Button("推广", sub_button2));
        buttons.add(new Button("麦链合伙", "view", PropertiesUtils.getStringValue("web.domain.name.address")));
        menu.setButton(buttons);

        String result = HttpClientUtils.httpPost(createMenu, JSONObject.toJSONString(menu));
        System.out.println(result);//JSONObject.toJSONString(menu));
    }
}
