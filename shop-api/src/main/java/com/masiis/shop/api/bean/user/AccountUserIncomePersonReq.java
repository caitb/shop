package com.masiis.shop.api.bean.user;

import com.masiis.shop.api.bean.base.BasePagingReq;

/**
 * Created by wangbingjian on 2016/8/10.
 */
public class AccountUserIncomePersonReq extends BasePagingReq {
    /**
     * 格式为yyyy-MM
     */
    private String date;
    /**
     * 用户id
     */
    private Long userId;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
