package com.masiis.shop.api.bean.shop;

import com.masiis.shop.api.bean.base.BasePagingReq;

/**
 * Created by wangbingjian on 2016/5/23.
 */
public class DistributionRecordReq extends BasePagingReq {

    private String token;

    /**
     * 日期字符串精确到月份 格式为"yyyy-MM"
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
