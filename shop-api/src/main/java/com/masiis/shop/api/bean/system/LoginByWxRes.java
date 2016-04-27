package com.masiis.shop.api.bean.system;

import com.masiis.shop.api.bean.base.BaseResModel;
import com.masiis.shop.common.annotation.SignField;

/**
 * @Date 2016/4/27
 * @Auther lzh
 */
public class LoginByWxRes extends BaseResModel{
    private String token;
    private Integer expire;
    private String expireUnit;
    private String userKey;
    private String nonceStr;
    @SignField
    private String sign;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getExpire() {
        return expire;
    }

    public void setExpire(Integer expire) {
        this.expire = expire;
    }

    public String getExpireUnit() {
        return expireUnit;
    }

    public void setExpireUnit(String expireUnit) {
        this.expireUnit = expireUnit;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
