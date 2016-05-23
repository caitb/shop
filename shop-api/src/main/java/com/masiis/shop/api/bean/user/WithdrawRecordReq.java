package com.masiis.shop.api.bean.user;

import com.masiis.shop.api.bean.base.BasePagingReq;

/**
 * Created by wangbingjian on 2016/5/6.
 */
public class WithdrawRecordReq extends BasePagingReq {

    private String token;
    /**
     * 格式为 yyyy-MM
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
