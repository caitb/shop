package com.masiis.shop.web.common.constants;

/**
 * Created by lzh on 2016/2/24.
 */
public class WxAuthConstants {
    /**
     * 授权页面链接
     */
    public static final String URL_AUTH = "https://open.weixin.qq.com/connect/oauth2/authorize";
    /**
     * appid
     */
    public static final String APPID = "wx7c874d5a102dccef";
    /**
     * appsecret
     */
    public static final String APPSECRET = "67f21b9a5df948c9dbf203393d8fb1a1";
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
     * 刷新token的grant_type类型
     */
    public static final String GRANT_TYPE_RFTOKEN = "refresh_token";

    public static final String GRANT_TYPE_ACCESSTOKEN = "authorization_code";
}