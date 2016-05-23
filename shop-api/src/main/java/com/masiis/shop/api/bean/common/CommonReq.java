package com.masiis.shop.api.bean.common;

import com.masiis.shop.api.bean.base.BaseReq;

/**
 * Created by wangbingjian on 2016/5/6.
 */
public class CommonReq extends BaseReq{

    private String token;

    private Long id;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
