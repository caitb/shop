package com.masiis.shop.api.bean.user;

import com.masiis.shop.api.bean.base.BasePagingReq;
import com.masiis.shop.api.bean.base.BaseReq;

/**
 * @Date 2016/5/3
 * @Auther lzh
 */
public class PartnerIndexReq extends BasePagingReq {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
