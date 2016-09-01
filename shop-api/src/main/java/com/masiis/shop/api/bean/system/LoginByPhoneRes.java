package com.masiis.shop.api.bean.system;

import com.masiis.shop.api.bean.base.BaseRes;

/**
 * @Date 2016/4/26
 * @Auther lzh
 */
public class LoginByPhoneRes extends BaseRes {
    private String token;
    private Integer expire;
    private String userKey;
    private String expireUnit;

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

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getExpireUnit() {
        return expireUnit;
    }

    public void setExpireUnit(String expireUnit) {
        this.expireUnit = expireUnit;
    }
}
