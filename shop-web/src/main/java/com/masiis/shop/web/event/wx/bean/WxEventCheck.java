package com.masiis.shop.web.event.wx.bean;

import com.masiis.shop.common.annotation.SignField;

/**
 * @Date 2016/5/6
 * @Auther lzh
 */
public class WxEventCheck {
    private String signature;
    private String token;
    private String timestamp;
    private String encrypt_type;
    private String msg_signature;
    private String nonce;
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

    public String getEncrypt_type() {
        return encrypt_type;
    }

    public void setEncrypt_type(String encrypt_type) {
        this.encrypt_type = encrypt_type;
    }

    public String getMsg_signature() {
        return msg_signature;
    }

    public void setMsg_signature(String msg_signature) {
        this.msg_signature = msg_signature;
    }

    @Override
    public String toString() {
        return "WxEventCheck{" +
                "signature='" + signature + '\'' +
                ", token='" + token + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", encrypt_type='" + encrypt_type + '\'' +
                ", msg_signature='" + msg_signature + '\'' +
                ", nonce='" + nonce + '\'' +
                ", echostr='" + echostr + '\'' +
                '}';
    }
}
