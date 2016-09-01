package com.masiis.shop.api.bean.shop;

import com.masiis.shop.api.bean.base.BasePagingReq;

/**
 * Created by wl on 2016/8/8.
 */
public class SpokesMapRef extends BasePagingReq {

    private String ID;

    private Integer queryType;

    public Integer getQueryType() { return queryType; }

    public void setQueryType(Integer queryType) { this.queryType = queryType; }

    public String getID() { return ID; }

    public void setID(String ID) {
        this.ID = ID;
    }

}
