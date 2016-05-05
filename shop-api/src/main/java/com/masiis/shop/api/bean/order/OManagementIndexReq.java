package com.masiis.shop.api.bean.order;

import com.masiis.shop.api.bean.base.BaseReq;

/**
 * @Date 2016/5/5
 * @Auther lzh
 */
public class OManagementIndexReq extends BaseReq {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
