package com.masiis.shop.admin.utils;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.admin.constants.wx.WxPayUserCons;
import com.masiis.shop.common.beans.wx.notice.BaseSend;
import com.masiis.shop.common.beans.wx.notice.TemplateNotPay;
import com.masiis.shop.common.util.HttpClientUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @Date:2016/4/20
 * @auth:lzh
 */
public class WxNoticeUtils {

    public static void main(String... args) throws UnsupportedEncodingException {
        String token = "U1nSzcep8goIcn18DXE47w9M0yMnG0dUf2VFsKPIvwhfN8vhydwBcc90fldjQ95ZjNrCt6NKr8vv-07vyBhD2EI7gLlPALsoVBlk939Cv-hiA-eIQT1sLdNCzSbMnsR5PRLeAJAUWQ";
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + token;
        /*String url1 = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+ WxPayUserCons.APPID+"&secret=" + WxPayUserCons.APPSECRET;
        String urlEn = URLEncoder.encode(url1, "UTF-8");
        System.out.println(urlEn);
        System.out.println(HttpClientUtils.httpGet(urlEn));*/
        /*String data = "{" +
                "\"touser\":\"ojLdDwksB_M2b5R9ZNIk3UdmfaA8\"," +
                "\"template_id\":\"DIQS5w9jXrEghsgcZK1PbwmaaFRCfmbndUTTcXoNeTQ\"," +
                "\"url\":\"http://www.baidu.com\"," +
                "\"topcolor\":\"#FF0000\"," +
                "\"data\":{" +
                "\"first\": {" +
                "\"value\":\"你好啊111\"," +
                "\"color\":\"#173177\"" +
                "}," +
                "\"type\":{" +
                "\"value\":\"合伙人\"," +
                "\"color\":\"#173177\"" +
                "}," +
                "\"e_title\":{" +
                "\"value\":\"AAA级合伙人\"," +
                "\"color\":\"#173177\"" +
                "}," +
                "\"o_id\":{" +
                "\"value\":\"LSHBSIDJS201604208349SSSSS\"," +
                "\"color\":\"#173177\"" +
                "}," +
                "\"order_date\":{" +
                "\"value\":\"2016-04-20 13:51:40\"," +
                "\"color\":\"#173177\"" +
                "}," +
                "\"o_money\":{" +
                "\"value\":\"25.55\"," +
                "\"color\":\"#173177\"" +
                "}," +
                "\"remark\":{" +
                "\"value\":\"您的订单还未支付,请尽快支付,立即支付\"," +
                "\"color\":\"#173177\"}}}";
        String result = HttpClientUtils.httpPost(url, data);
        System.out.println(result);*/
        /*String url2 = "https://api.weixin.qq.com/cgi-bin/template/get_all_private_template?access_token=" + token;
        System.out.println(HttpClientUtils.httpGet(url2));*/
        BaseSend s = new BaseSend();
        s.setData(new TemplateNotPay());
        System.out.println(JSONObject.toJSONString(s));
    }
}
