package com.masiis.shop.api.bean.user;

import com.masiis.shop.api.bean.base.BaseReq;

/**
 * Created by wangbingjian on 2016/5/5.
 */
public class PersonalCenterReq extends BaseReq {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
