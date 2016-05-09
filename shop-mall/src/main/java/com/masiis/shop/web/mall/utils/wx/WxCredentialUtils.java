package com.masiis.shop.web.mall.utils.wx;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.constant.wx.WxConsPF;
import com.masiis.shop.common.util.HttpClientUtils;
import com.masiis.shop.web.mall.beans.wxauth.CredentialAccessTokenRes;
import com.masiis.shop.web.mall.utils.SpringCommonRdUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.Date;

/**
 * @Date:2016/4/22
 * @auth:lzh
 */
public class WxCredentialUtils {
    private static Logger log = Logger.getLogger(WxCredentialUtils.class);
    private static final String REDIS_CREDENTIAL_ACCESS_TOKEN_NAME = "credential_access_token_RSs&^%$";
    private static final String REDIS_CREDENTIAL_ACCESS_TOKEN_EXPIRES_NAME = "credential_access_token_expires_RSs&^%$";

    private static class Holder {
        private static final WxCredentialUtils INSTANCE = new WxCredentialUtils();
    }
    private WxCredentialUtils(){}
    // 单例懒加载
    public static final WxCredentialUtils getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * 获取公众平台access_token
     *
     * @return
     */
    public String getCredentialAccessToken(String appId, String secret) {
        String resTokne = getTokenFromRedis(appId, secret);
        if(StringUtils.isNotBlank(resTokne)){
            return resTokne;
        }
        try {
            synchronized(this){
                resTokne = getTokenFromRedis(appId, secret);
                if(StringUtils.isNotBlank(resTokne)){
                    return resTokne;
                }
                // 重新请求token
                String tokenUrl = WxConsPF.URL_JSSDK_ACCESS_TOKEN
                        + "?grant_type=" + WxConsPF.JSSDK_GRANT_TYPE
                        + "&appid=" + appId
                        + "&secret=" + secret;
                String res = HttpClientUtils.httpGet(tokenUrl);
                CredentialAccessTokenRes tokenRes = JSONObject.parseObject(res, CredentialAccessTokenRes.class);
                if (StringUtils.isNotBlank(tokenRes.getAccess_token())) {
                    String token = tokenRes.getAccess_token();
                    Long expire = Long.valueOf(tokenRes.getExpires_in());
                    Date expireDate = new Date(new Date().getTime() + expire);
                    SpringCommonRdUtil.saveEx(REDIS_CREDENTIAL_ACCESS_TOKEN_NAME + appId, token, expire - 6l);
                    SpringCommonRdUtil.saveEx(REDIS_CREDENTIAL_ACCESS_TOKEN_EXPIRES_NAME + appId, expireDate,
                            expire - 10l);
                    return token;
                }
            }
        } catch (Exception e) {
            log.error(e);
        }
        return null;
    }

    private String getTokenFromRedis(String appId, String secret){
        String tokenOri = SpringCommonRdUtil.get(REDIS_CREDENTIAL_ACCESS_TOKEN_NAME + appId, String.class);
        Date exDate = SpringCommonRdUtil.get(REDIS_CREDENTIAL_ACCESS_TOKEN_EXPIRES_NAME + appId, Date.class);
        if(StringUtils.isNotBlank(tokenOri)
                && exDate != null
                && exDate.compareTo(new Date()) > 0){
            // token有效
            return tokenOri;
        }
        return null;
    }

    /**
     * 刷新
     *
     * @param appId
     * @param secret
     * @return
     */
    public synchronized String refreshCredentialAccessToken(String appId, String secret) {
        // 重新请求token
        String tokenUrl = WxConsPF.URL_JSSDK_ACCESS_TOKEN
                + "?grant_type=" + WxConsPF.JSSDK_GRANT_TYPE
                + "&appid=" + appId
                + "&secret=" + secret;
        try {
            log.info("刷新" + appId + "的accessToken开始...");
            String res = HttpClientUtils.httpGet(tokenUrl);
            CredentialAccessTokenRes tokenRes = JSONObject.parseObject(res, CredentialAccessTokenRes.class);
            if (StringUtils.isNotBlank(tokenRes.getAccess_token())) {
                String token = tokenRes.getAccess_token();
                Long expire = Long.valueOf(tokenRes.getExpires_in());
                Date expireDate = new Date(new Date().getTime() + expire);
                SpringCommonRdUtil.saveEx(REDIS_CREDENTIAL_ACCESS_TOKEN_NAME + appId, token, expire - 6l);
                SpringCommonRdUtil.saveEx(REDIS_CREDENTIAL_ACCESS_TOKEN_EXPIRES_NAME + appId, expireDate,
                        expire - 10l);

                log.info("刷新" + appId + "的accessToken成功,结束...");
                return token;
            }
            log.info("res:" + res);
            log.error("刷新" + appId + "的accessToken失败...");
        } catch (Exception e) {
            log.error("刷新credential_access_token失败");
        }
        return null;
    }
}
