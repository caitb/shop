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
    public static final String WX_PF_TM_ID_PTNER_APPLY_OK = WxPropertiesUtils.getStringValue("wx.conf.pf.WX_PF_TM_ID_PTNER_APPLY_OK");
    /**
     * 下线加入通知
     */
    public static final String WX_PF_TM_ID_PTNER_JOIN_NOTICE = WxPropertiesUtils.getStringValue("wx.conf.pf.WX_PF_TM_ID_PTNER_JOIN_NOTICE");
    /**
     * 实名认证审核结通知
     */
    public static final String WX_PF_TM_ID_AUTH_NOTICE = WxPropertiesUtils.getStringValue("wx.conf.pf.WX_PF_TM_ID_AUTH_NOTICE");
    /**
     * 实名认证资料提交通知
     */
    public static final String WX_PF_TM_ID_RM_SUBMIT = WxPropertiesUtils.getStringValue("wx.conf.pf.WX_PF_TM_ID_RM_SUBMIT");
    /**
     * 订单发货通知
     */
    public static final String WX_PF_TM_ID_ORDER_SHIPPED = WxPropertiesUtils.getStringValue("wx.conf.pf.WX_PF_TM_ID_ORDER_SHIPPED");
    /**
     * 新代理订单提醒
     */
    public static final String WX_PF_TM_ID_NEW_PF_ORDER = WxPropertiesUtils.getStringValue("wx.conf.pf.WX_PF_TM_ID_NEW_PF_ORDER");
    /**
     * 代理提现申请
     */
    public static final String WX_PF_TM_ID_EXTRACT_APPLY = WxPropertiesUtils.getStringValue("wx.conf.pf.WX_PF_TM_ID_EXTRACT_APPLY");
    /**
     * 提现结果通知
     */
    public static final String WX_PF_TM_ID_EXTRACT_APPLY_SUCCESS = WxPropertiesUtils.getStringValue("wx.conf.pf.WX_PF_TM_ID_EXTRACT_APPLY_SUCCESS");
    /**
     * 新订单通知(详细)(补货成功提醒;进入排单提醒;处理排单提醒)
     */
    public static final String WX_PF_TM_ID_NEW_ORDER_DETAIL = WxPropertiesUtils.getStringValue("wx.conf.pf.WX_PF_TM_ID_NEW_ORDER_DETAIL");
    /**
     * 库存不足预警
     */
    public static final String WX_PF_TM_ID_INVENTORY_SHORTAGE = WxPropertiesUtils.getStringValue("wx.conf.pf.WX_PF_TM_ID_INVENTORY_SHORTAGE");
    /**
     * 小铺新订单提醒
     */
    public static final String WX_PF_TM_ID_NEW_SHOP_ORDER = WxPropertiesUtils.getStringValue("wx.conf.pf.WX_PF_TM_ID_NEW_SHOP_ORDER");
    /**
     * 代理订单确认收货通知
     */
    public static final String WX_PF_TM_ID_ORDER_CONFIRM = WxPropertiesUtils.getStringValue("wx.conf.pf.WX_PF_TM_ID_ORDER_CONFIRM");
    /**
     * 线下支付提醒
     */
    public static final String WX_PF_TM_ID_OFFLINE_PAY = WxPropertiesUtils.getStringValue("wx.conf.pf.WX_PF_TM_ID_OFFLINE_PAY");
    /**
     * 升级申请通知
     */
    public static final String WX_PF_TM_ID_UP_APPLY_NOTICE = WxPropertiesUtils.getStringValue("wx.conf.pf.WX_PF_TM_ID_UP_APPLY_NOTICE");
    /**
     * 升级通知单或者升级订单取消通知
     */
    public static final String WX_PF_TM_ID_UP_CANCEL_NOTICE = WxPropertiesUtils.getStringValue("wx.conf.pf.WX_PF_TM_ID_UP_CANCEL_NOTICE");
    /**
     * 升级申请结果通知
     */
    public static final String WX_PF_TM_ID_UP_RESULT_NOTICE = WxPropertiesUtils.getStringValue("wx.conf.pf.WX_PF_TM_ID_UP_RESULT_NOTICE");
    /**
     * 升级成功给上级通知
     */
    public static final String WX_PF_TM_ID_UP_SUCCESS_NOTICE = WxPropertiesUtils.getStringValue("wx.conf.pf.WX_PF_TM_ID_UP_SUCCESS_NOTICE");
    /**
     * 推荐成功通知
     */
    public static final String WX_PF_TM_ID_RECOMMEND_SUCCESS_NOTICE = WxPropertiesUtils.getStringValue("wx.conf.pf.WX_PF_TM_ID_RECOMMEND_SUCCESS_NOTICE");
    /**
     * 推荐佣金提醒
     */
    public static final String WX_PF_TM_ID_RECOMMEND_PROFIT_IN = WxPropertiesUtils.getStringValue("wx.conf.pf.WX_PF_TM_ID_RECOMMEND_PROFIT_IN");
    /**
     * 订单取消通知
     */
    public static final String WX_PF_TM_ID_ORDER_CANCEL_NOTICE = WxPropertiesUtils.getStringValue("wx.conf.pf.WX_PF_TM_ID_ORDER_CANCEL_NOTICE");
}
