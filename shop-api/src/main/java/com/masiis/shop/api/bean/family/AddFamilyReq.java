package com.masiis.shop.api.bean.family;

import com.masiis.shop.api.bean.base.BaseReq;

/**
 * Created by jiajinghao on 2016/8/8.
 */
public class AddFamilyReq extends BaseReq{

    private String token;

    private String userName;//姓名

    private String phone;//手机号

    private String wxCode; //微信号

    private Integer skuId;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWxCode() {
        return wxCode;
    }

    public void setWxCode(String wxCode) {
        this.wxCode = wxCode;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }
}
