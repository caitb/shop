package com.masiis.shop.api.bean.order;

import com.masiis.shop.api.bean.base.BasePagingReq;

/**
 * @Date 2016/5/5
 * @Auther lzh
 */
public class OrderListPagingReq extends BasePagingReq {
    private String token;
    private Integer ostatus;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getOstatus() {
        return ostatus;
    }

    public void setOstatus(Integer ostatus) {
        this.ostatus = ostatus;
    }
}
