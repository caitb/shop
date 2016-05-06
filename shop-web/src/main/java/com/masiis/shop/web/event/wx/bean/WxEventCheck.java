package com.masiis.shop.web.event.wx.bean;

import com.masiis.shop.common.annotation.SignField;

/**
 * @Date 2016/5/6
 * @Auther lzh
 */
public class WxEventCheck {
    @SignField
    private String signature;
    private String token;
    private String timestamp;
    private String nonce;
    @SignField
    private String echostr;

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getEchostr() {
        return echostr;
    }

    public void setEchostr(String echostr) {
        this.echostr = echostr;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
