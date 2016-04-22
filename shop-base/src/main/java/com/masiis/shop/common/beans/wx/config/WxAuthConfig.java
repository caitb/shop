package com.masiis.shop.common.beans.wx.config;

/**
 * @Date:2016/4/21
 * @auth:lzh
 */
public class WxAuthConfig extends WxBaseConfig{
    private String authToken;
    private String refreshToken;
    private String openId;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
