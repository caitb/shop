package com.masiis.shop.api.bean.shop;

import com.alibaba.fastjson.annotation.JSONField;
import com.masiis.shop.api.bean.base.BasePagingReq;

/**
 * Created by hw on 2016/7/29.
 */
public class SpokesMapReq extends BasePagingReq {
    @JSONField(name = "ID")
    private String ID;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

}
