package com.masiis.shop.common.beans.wx.config;

/**
 * @Date:2016/4/21
 * @auth:lzh
 */
public class WxBaseConfig {
    private String appId;
    private String secret;
    private String mchId;
    private String apiKey;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
