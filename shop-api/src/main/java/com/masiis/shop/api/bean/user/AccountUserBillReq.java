package com.masiis.shop.api.bean.user;

import com.masiis.shop.api.bean.base.BaseReq;

/**
 * Created by wangbingjian on 2016/5/6.
 */
public class AccountUserBillReq extends BaseReq{

    private String token;
    /**
     * 格式为yyyyMM
     */
    private String date;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
