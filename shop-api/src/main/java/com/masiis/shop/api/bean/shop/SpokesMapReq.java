package com.masiis.shop.api.bean.shop;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.masiis.shop.api.bean.base.BasePagingReq;

/**
 * Created by hw on 2016/7/29.
 */
public class SpokesMapReq extends BasePagingReq {
    @JSONField(name = "ID")
    private String iD;

    public String getID() {
        return iD;
    }

    public void setID(String ID) {
        this.iD = ID;
    }

    public static void main(String... args) {
        SpokesMapReq req = new SpokesMapReq();
        req.setID("aaa");
        System.out.println(JSONObject.toJSONString(req));
    }

}
