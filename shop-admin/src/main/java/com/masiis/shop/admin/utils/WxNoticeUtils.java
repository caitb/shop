package com.masiis.shop.admin.utils;

import java.io.UnsupportedEncodingException;

/**
 * @Date:2016/4/20
 * @auth:lzh
 */
public class WxNoticeUtils {

    public static void main(String... args) throws UnsupportedEncodingException {
        String token = "QN5UVSb0HyohEI3zOUk1kj8ng1ipXw0aRZbfSPc5LaWdaiWSYw-kb1vsLsPRCtU4VQDj4HfCXR5DYWHnFvjf9zLALy46yWerLmWuegYut4nJAPkCe9kuHLSRJcV8jbweWYLjAAAACP";
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + token;
        /*String url1 = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+ WxConsSF.APPID+"&secret=" + WxConsSF.APPSECRET;
        String urlEn = URLEncoder.encode(url1, "UTF-8");
        System.out.println(urlEn);
        System.out.println(HttpClientUtils.httpGet(urlEn));*/

        /*String url2 = "https://api.weixin.qq.com/cgi-bin/template/get_all_private_template?access_token=" + token;
        System.out.println(HttpClientUtils.httpGet(url2));*/

        /*TemplateNotPay notPay = new TemplateNotPay();
        notPay.setE_title(new TemplateDataItem("AAA级合伙人", "#173177"));
        notPay.setFirst(new TemplateDataItem("您的订单还未支付", "#173177"));
        notPay.setO_id(new TemplateDataItem("LSHBSIDJS201604208349DDDDDDD", "#173177"));
        notPay.setO_money(new TemplateDataItem("30.23", "#173177"));
        notPay.setOrder_date(new TemplateDataItem("2016-04-21 11:35:56", "#173177"));
        notPay.setType(new TemplateDataItem("合伙人", "#173177"));
        notPay.setRemark(new TemplateDataItem("请尽快支付", "#173177"));
        TemplateBase base = new TemplateBase(notPay);
        base.setTouser("ojLdDwksB_M2b5R9ZNIk3UdmfaA8");
        base.setTemplate_id("DIQS5w9jXrEghsgcZK1PbwmaaFRCfmbndUTTcXoNeTQ");

        String data = JSONObject.toJSONString(base);
        System.out.println(data);
        String result = HttpClientUtils.httpPost(url, data);
        System.out.println(result);*/
    }
}
