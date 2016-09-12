package com.masiis.shop.api.bean.system;

import com.masiis.shop.api.bean.base.BaseReq;

/**
 * Created by jiajinghao on 2016/9/12.
 */
public class CommonActivityReq extends BaseReq {

    private String token;

    private String pbbannerLink;//活动索引

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPbbannerLink() {
        return pbbannerLink;
    }

    public void setPbbannerLink(String pbbannerLink) {
        this.pbbannerLink = pbbannerLink;
    }
}
