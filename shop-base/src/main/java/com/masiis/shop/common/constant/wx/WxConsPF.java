package com.masiis.shop.common.constant.wx;

import com.masiis.shop.common.util.WxPropertiesUtils;

/**
 * Created by lzh on 2016/2/24.
 */
public class WxConsPF {
    /**
     * 授权页面链接
     */
    public static final String URL_AUTH = "https://open.weixin.qq.com/connect/oauth2/authorize";
    /**
     * appid
     */
    public static final String APPID = WxPropertiesUtils.getStringValue("wx.conf.pf.APPID"); //"wxd5afa1deb29c6197"; //麦士集团appid"wx7c874d5a102dccef";
    /**
     * appsecret
     */
    public static final String APPSECRET = WxPropertiesUtils.getStringValue("wx.conf.pf.APPSECRET"); //"d0c6c73cbc769450a554a2623d2c45ea"; //麦士集团appsecret"67f21b9a5df948c9dbf203393d8fb1a1";
    /**
     * 获取accesstoken链接
     */
    public static final String REDIECT_URI_GET_ACCESS_TOKEN = "verify/actk";
    /**
     * 获取accesstoken链接
     */
    public static final String REDIECT_URI_BASE_AUTH = "verify/bactk";
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
    public static final String SCOPE_AUTH_BASE = "snsapi_base";
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
    /**
     * 微信公众号二维码创建地址
     */
    public static final String URL_CREATE_WEIXIN_PUBLIC_NUMBER_QRCODE = "https://api.weixin.qq.com/cgi-bin/qrcode/create";
    /**
     * 微信公众号二维码请求地址
     */
    public static final String URL_WEIXIN_PUBLIC_NUMBER_QRCODE = "https://mp.weixin.qq.com/cgi-bin/showqrcode";

    /*===============================================================================================================*/
    /**
     * 微信支付签名密钥
     */
    public static final String WX_PAY_SIGN_KEY = WxPropertiesUtils.getStringValue("wx.conf.pf.WX_PAY_SIGN_KEY"); //"U0SJD1OR4WLA5J8QM9IZAJT5KC4ZLS7D";
    /**
     * 商户号
     */
    public static final String WX_PAY_MCHID = WxPropertiesUtils.getStringValue("wx.conf.pf.WX_PAY_MCHID"); //"1319531601";
    /**
     * 统一下单url
     */
    public static final String WX_PAY_URL_UNIORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    /**
     * 订单异步回调通知地址
     */
    public static final String WX_PAY_URL_UNIORDER_NOTIFY = WxPropertiesUtils.getStringValue("wx.conf.pf.WX_PAY_URL_UNIORDER_NOTIFY"); //"http://weixin.masiis.com/wxntfy/orderNtfy";


    /*===============================================================================================================*/
    /**
     * 合伙人申请成功提示
     */
    public static final String WX_PF_TM_ID_PTNER_APPLY_OK = "ynmYfDzdSp22Ohaekl3B0TFVZPFVQrX2EfT5soEQo90";
    /**
     * 下线加入通知
     */
    public static final String WX_PF_TM_ID_PTNER_JOIN_NOTICE = "w6oDOs9sYwujT3bAnimYQi8JC6G__yg1hJt8dJ3N95Q";
    /**
     * 实名认证审核结通知
     */
    public static final String WX_PF_TM_ID_AUTH_NOTICE = "CLkaQaL4eEuO9TP4VHwBVsuivYPWyqdJ5uSbHrVHrwI";
    /**
     * 实名认证资料提交通知
     */
    public static final String WX_PF_TM_ID_RM_SUBMIT = "sZEOLMywZ8_GXW44K21oXzCZdc772kX7G1dzIyyyiSQ";
    /**
     * 订单发货通知
     */
    public static final String WX_PF_TM_ID_ORDER_SHIPPED = "f8k2LudPdd_BfoNCC0HVK-bj53VZc1My0HlDWEH29uU";
    /**
     * 新代理订单提醒
     */
    public static final String WX_PF_TM_ID_NEW_PF_ORDER = "EJlLVU0QqOgWSfZnlbxHj17tphR3HO68NR4rPk6oI6U";
    /**
     * 代理提现申请
     */
    public static final String WX_PF_TM_ID_EXTRACT_APPLY = "V7BYNEQv-_3bX9KENLjRmhY-ROXlGmoBHCYoxSqSGhs";
    /**
     * 提现结果通知
     */
    public static final String WX_PF_TM_ID_EXTRACT_APPLY_SUCCESS = "ze3Hn4bt8_fTxxUv9V3WVeK70RyfxqEcXCOW6IREKto";
    /**
     * 新订单通知(详细)(补货成功提醒;进入排单提醒;处理排单提醒)
     */
    public static final String WX_PF_TM_ID_NEW_ORDER_DETAIL = "qWbY38zqoRLz2iWxK6Qtjewflm8W2XNt-DJWYL0R07Y";
    /**
     * 库存不足预警
     */
    public static final String WX_PF_TM_ID_INVENTORY_SHORTAGE = "H1eh8fdG04b00J2kyZhRDYov3y6NHlrBra822P-fy4I";
    /**
     * 小铺新订单提醒
     */
    public static final String WX_PF_TM_ID_NEW_SHOP_ORDER = "r0tjUdJA1StfjOxVTYom0UWwL7WSpq9lGwzIApWq3g0";
    /**
     * 代理订单确认收货通知
     */
    public static final String WX_PF_TM_ID_ORDER_CONFIRM = "cmHM5OuaNpnkJvDyBsRFLARSl5MdAV35MZqAp3r_XHI";
    /**
     * 线下支付提醒
     */
    public static final String WX_PF_TM_ID_OFFLINE_PAY = "AO0NRZmPuWK7TbVjDIP-_oEXwzavqLvgSmnEquYFQMo";
}
