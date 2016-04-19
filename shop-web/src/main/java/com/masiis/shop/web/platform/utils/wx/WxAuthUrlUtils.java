package com.masiis.shop.web.platform.utils.wx;

import com.masiis.shop.web.platform.constants.WxConstants;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @Date:2016/4/18
 * @auth:lzh
 */
public class WxAuthUrlUtils {

    /**
     * 创建授权页面链接,需用户授权确认
     *
     * @param basepath
     * @param stateStr
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String createAuthorizeInfoUrl(String basepath, String stateStr) throws UnsupportedEncodingException {
        return WxConstants.URL_AUTH
                + "?appid=" + WxConstants.APPID
                + "&redirect_uri=" + URLEncoder.encode(basepath + WxConstants.REDIECT_URI_GET_ACCESS_TOKEN, "UTF-8")
                + "&response_type=" + WxConstants.RESPONSE_TYPE_AUTH
                + "&scope=" + WxConstants.SCOPE_AUTH_USRINFO
                + "&state=" + stateStr
                + WxConstants.TAILSTR_AUTH;
    }

    /**
     * 创建获取用户信息链接
     *
     * @param openId
     * @return
     */
    public static String createUserInfoUrl(String accessToken, String openId){
        return WxConstants.URL_GET_USERINFO
                + "?access_token=" + accessToken
                + "&openid=" + openId
                + "&lang=zh_CN";
    }

    /**
     * 获取微信用户的accesstoken
     *
     * @param code
     * @return
     */
    public static String createAccessTokenUrl(String code){
        return WxConstants.URL_GET_ACCESS_TOKEN
                + "?appid=" + WxConstants.APPID
                + "&secret=" + WxConstants.APPSECRET
                + "&code=" + code
                + "&grant_type=" + WxConstants.GRANT_TYPE_ACCESSTOKEN;
    }

    /**
     * 创建刷新token链接
     *
     * @param refreshToken
     * @return
     */
    public static String createRefreshTokenUrl(String refreshToken){
        return WxConstants.URL_REFRESH_TOKEN
                + "?appid=" + WxConstants.APPID
                + "&grant_type=" + WxConstants.GRANT_TYPE_RFTOKEN
                + "&refresh_token=" + refreshToken;
    }

    public static String createCheckTokenUrl(String token, String openId){
        return WxConstants.URL_CHECK_ACCESS_TOKEN
                + "?access_token=" + token
                + "&openid=" + openId;
    }

    public static String createAuthorizeBaseUrl(String basepath, String stateStr) throws UnsupportedEncodingException {
        return WxConstants.URL_AUTH
                + "?appid=" + WxConstants.APPID
                + "&redirect_uri=" + URLEncoder.encode(basepath + WxConstants.REDIECT_URI_BASE_AUTH, "UTF-8")
                + "&response_type=" + WxConstants.RESPONSE_TYPE_AUTH
                + "&scope=" + WxConstants.SCOPE_AUTH_BASE
                + "&state=" + stateStr
                + WxConstants.TAILSTR_AUTH;
    }
}
