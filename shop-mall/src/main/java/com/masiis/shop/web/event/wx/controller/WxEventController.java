package com.masiis.shop.web.event.wx.controller;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.constant.wx.WxConsPF;
import com.masiis.shop.common.constant.wx.WxConsSF;
import com.masiis.shop.common.util.HttpClientUtils;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.common.util.SHAUtils;
import com.masiis.shop.common.util.WxPropertiesUtils;
import com.masiis.shop.web.event.wx.bean.event.WxBaseMessage;
import com.masiis.shop.web.event.wx.bean.event.WxEventBody;
import com.masiis.shop.web.event.wx.bean.event.WxEventCheck;
import com.masiis.shop.web.event.wx.bean.menu.Button;
import com.masiis.shop.web.event.wx.bean.menu.Menu;
import com.masiis.shop.web.event.wx.service.WxEventService;
import com.masiis.shop.web.mall.controller.base.BaseController;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
    private static final String token = WxPropertiesUtils.getStringValue("wx.conf.sf.WX_EVENT_RECEIVE_TOKEN");
    private static final String encodingAESKey = WxPropertiesUtils.getStringValue("wx.conf.sf.WX_EVENT_RECEIVE_ENCODINGKEY");
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private WxEventService wxEventService;

    @RequestMapping("/check")
    @ResponseBody
    public String receiveWxEvent(HttpServletRequest request, WxEventCheck req) throws IOException {
        log.info("getReq:" + req.toString());
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
            log.info("wxEvent:" + requestBody);
            XStream xStream = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
            xStream.ignoreUnknownElements();
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
            log.info("eventRes:" + resStr);
            return resStr;

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return "fail";
    }

    public static void main(String... args) throws UnsupportedEncodingException {
        String url1 = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+ WxConsSF.APPID+"&secret=" + WxConsSF.APPSECRET;
        //String url1 = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxd5afa1deb29c6197&secret=d0c6c73cbc769450a554a2623d2c45ea";
        String urlEn = URLEncoder.encode(url1, "UTF-8");
        /*System.out.println(urlEn);
        System.out.println(HttpClientUtils.httpGet(urlEn));*/

        String createMenu = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=wmkhCHk7jrg_vWXovHxCtrOcb5pJ-7siqSIvk0_Uhpgkygdpe9FxlNtTY-GoQJ0M2eZpl5gwu1THUtZ04wQoQTlDjxYJxhkMksEbcZaUOVoGIUbAGAMOA";
        Menu menu = new Menu();
        List<Button> buttons = new ArrayList<>();
        List<Button> sub_button1 = new ArrayList<>();
        sub_button1.add(new Button("关于麦链", "view", "http://mp.weixin.qq.com/s?__biz=MzI1OTIxNzgwNA==&mid=100000004&idx=1&sn=d50e511bed4075a46d9a649d254edcbd&scene=18#wechat_redirect"));
        sub_button1.add(new Button("新闻报道", "view", "http://invest.china.com.cn/html/2016/zhuantibaodao_0108/46155.html"));
        Button b1 = new Button("联系我们", "click", null);
        b1.setKey("menu_click_event_contact_us");
        sub_button1.add(b1);

        buttons.add(new Button("关于麦链", sub_button1));
        buttons.add(new Button("分享赚钱", "view", PropertiesUtils.getStringValue("mall.domain.name.address") + "/shopview/home.shtml?fm=0"));
        buttons.add(new Button("个人中心", "view", PropertiesUtils.getStringValue("mall.domain.name.address") + "/sfOrderManagerController/toBorderManagement?fm=0"));
        menu.setButton(buttons);

        String result = HttpClientUtils.httpPost(createMenu, JSONObject.toJSONString(menu));
        System.out.println(result);
    }
}
