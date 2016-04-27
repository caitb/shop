package com.masiis.shop.web.mall.beans.wxauth;

/**
 * @Date:2016/4/22
 * @auth:lzh
 */
public class CredentialAccessTokenRes {
    private String access_token;
    /**
     * 过期时间,单位(秒)
     */
    private String expires_in;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }
}
