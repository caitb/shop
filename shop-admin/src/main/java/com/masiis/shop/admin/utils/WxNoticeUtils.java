package com.masiis.shop.admin.utils;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.constant.wx.WxConsSF;
import com.masiis.shop.common.beans.wx.notice.WxNoticeDataItem;
import com.masiis.shop.common.beans.wx.notice.WxNoticeNotPay;
import com.masiis.shop.common.beans.wx.notice.WxNoticeReq;
import com.masiis.shop.common.util.HttpClientUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @Date:2016/4/20
 * @auth:lzh
 */
public class WxNoticeUtils {

    public static void main(String... args) throws UnsupportedEncodingException {
        String token = "9Bv6IwOERWqYtbQ1MjUjMmUx-GNKo9O1wtkNZT6hmkj2ZXFljZtHT60S15cTHVKc1yxGWB_1xXEMScQSrE2J8yOQSskxabWjYOvZwKxkac7Cg3EtNXGrApcgIVT7qyorTFZjAEAOAU";
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + token;
        String url1 = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+ WxConsSF.APPID+"&secret=" + WxConsSF.APPSECRET;
        String urlEn = URLEncoder.encode(url1, "UTF-8");
        System.out.println(urlEn);
        System.out.println(HttpClientUtils.httpGet(urlEn));

        /*String url2 = "https://api.weixin.qq.com/cgi-bin/template/get_all_private_template?access_token=" + token;
        System.out.println(HttpClientUtils.httpGet(url2));*/

        WxNoticeNotPay notPay = new WxNoticeNotPay();
        notPay.setE_title(new WxNoticeDataItem("AAA级合伙人", "#173177"));
        notPay.setFirst(new WxNoticeDataItem("您的订单还未支付", "#173177"));
        notPay.setO_id(new WxNoticeDataItem("LSHBSIDJS201604208349EEEEE", "#173177"));
        notPay.setO_money(new WxNoticeDataItem("36.23", "#173177"));
        notPay.setOrder_date(new WxNoticeDataItem("2016-04-21 14:35:56", "#173177"));
        notPay.setType(new WxNoticeDataItem("合伙人", "#173177"));
        notPay.setRemark(new WxNoticeDataItem("请尽快支付", "#173177"));
        WxNoticeReq base = new WxNoticeReq(notPay);
        base.setTouser("ojLdDwksB_M2b5R9ZNIk3UdmfaA8");
        base.setTemplate_id("DIQS5w9jXrEghsgcZK1PbwmaaFRCfmbndUTTcXoNeTQ");

        String data = JSONObject.toJSONString(base);
        System.out.println(data);
        String result = HttpClientUtils.httpPost(url, data);
        System.out.println(result);
    }
}
