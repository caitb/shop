package com.masiis.shop.common.constant.wx;

import com.masiis.shop.common.util.WxPropertiesUtils;

/**
 * Created by lzh on 2016/2/24.
 */
public class WxConsSF {
    /**
     * 授权页面链接
     */
    public static final String URL_AUTH = "https://open.weixin.qq.com/connect/oauth2/authorize";
    /**
     * appid
     */
    public static final String APPID = WxPropertiesUtils.getStringValue("wx.conf.sf.APPID"); //"wxd5afa1deb29c6197"; //麦士集团appid"wx7c874d5a102dccef";
    /**
     * appsecret
     */
    public static final String APPSECRET = WxPropertiesUtils.getStringValue("wx.conf.sf.APPSECRET"); //"d0c6c73cbc769450a554a2623d2c45ea"; //麦士集团appsecret"67f21b9a5df948c9dbf203393d8fb1a1";
    /**
     * 获取accesstoken链接
     */
    public static final String REDIECT_URI_GET_ACCESS_TOKEN = "verify/actk";
    /**
     * 授权页面链接参数
     */
    public static final String RESPONSE_TYPE_AUTH = "code";
    /**
     * 页面确认授权的可选参数
     */
    public static final String SCOPE_AUTH_USRINFO = "snsapi_userinfo";
    /**
     * 静默授权的可选参数
     */
    public static final String SCOPE_AUTH_BASE = "snsapi_BASE";
    /**
     * 授权链接尾部带着部分
     */
    public static final String TAILSTR_AUTH = "#wechat_redirect";
    /**
     * 根据code请求获取access_token的请求链接
     */
    public static final String URL_GET_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token";
    /**
     * 检查openid和token的有效性
     */
    public static final String URL_CHECK_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/auth";
    /**
     * 刷新token
     */
    public static final String URL_REFRESH_TOKEN = "https://api.weixin.qq.com/sns/oauth2/refresh_token";
    /**
     * 获取微信用户信息
     */
    public static final String URL_GET_USERINFO = "https://api.weixin.qq.com/sns/userinfo";
    /**
     * 提醒微信用户url
     */
    public static final String URL_WX_NOTICE = "https://api.weixin.qq.com/cgi-bin/message/template/send";
    /**
     * 获取用户信息(包括是否关注公众号)
     */
    public static final String URL_CGIBIN_USERINFO = "https://api.weixin.qq.com/cgi-bin/user/info";
    /**
     * 刷新token的grant_type类型
     */
    public static final String GRANT_TYPE_RFTOKEN = "refresh_token";

    public static final String GRANT_TYPE_ACCESSTOKEN = "authorization_code";
    /**
     * JSSDK:access_token获取地址
     */
    public static final String URL_JSSDK_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token";
    /**
     * JSSDK:grant_type
     */
    public static final String JSSDK_GRANT_TYPE = "client_credential";
    /**
     * JSSDK:jsapi_ticket获取地址
     */
    public static final String URL_JSSDK_JSAPI_TICKET = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";

    /*===============================================================================================================*/
    /**
     * 微信支付签名密钥
     */
    public static final String WX_PAY_SIGN_KEY = WxPropertiesUtils.getStringValue("wx.conf.sf.WX_PAY_SIGN_KEY"); //"U0SJD1OR4WLA5J8QM9IZAJT5KC4ZLS7D";
    /**
     * 商户号
     */
    public static final String WX_PAY_MCHID = WxPropertiesUtils.getStringValue("wx.conf.sf.WX_PAY_MCHID"); //"1319531601";
    /**
     * 统一下单url
     */
    public static final String WX_PAY_URL_UNIORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    /**
     * 订单异步回调通知地址
     */
    public static final String WX_PAY_URL_UNIORDER_NOTIFY = WxPropertiesUtils.getStringValue("wx.conf.sf.WX_PAY_URL_UNIORDER_NOTIFY"); //"http://weixin.masiis.com/wxntfy/orderNtfy";
    /**
     * 企业给用户打款url
     */
    public static final String WX_PAY_URL_USER = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
    /**
     * 企业给用户打款证书路径
     */
    public static final String PATH_CERT = "WEB-INF/files/apiclient_cert.p12";

    /*==================================================================================================================*/
    /**
     * 小铺订单下单通知(通知麦链商城)
     */
    public static final String WX_SF_TM_ID_ORDER_CREATE = "G4sZsZ9AQilP-bO-WPMPkDnwkcjpROn4h-ErZJKcoZU";
    /**
     * 小铺订单发货通知
     */
    public static final String WX_SF_TM_ID_ORDER_SHIP = "pcOehP8XA9o1pTTB9aX_fRJxnP7xZxesNE78SmIAVIE";
    /**
     * 小铺订单确认收货
     */
    public static final String WX_SF_TM_ID_ORDER_CONFIRM = "fwJRl-yYTGgbzdlcktSPdezOmBgtsRENB_EEBLztJCo";
    /**
     * 佣金提醒
     */
    public static final String WX_SF_TM_ID_PROFIT_IN = "hkCjoUlNOAg56hRD7QtOZ4mHG06pZ04fMY6aW0bwuOY";
    /**
     * 提现申请通知
     */
    public static final String WX_SF_TM_ID_EXTRACT_APPLY = "OEzsSSLWz10Xcj_c5-cwfavX81lc1kL8jSxH_sBvuVk";
    /**
     * 提现结果通知
     */
    public static final String WX_SF_TM_ID_EXTRACT_RESULT = "dAzmZlN8YX8N0E4fjFDY2ocVaPxjnQfwuFhrtdZo_Pg";
}
