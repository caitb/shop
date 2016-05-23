package com.masiis.shop.api.bean.user;

import com.masiis.shop.api.bean.base.BaseReq;

/**
 * Created by wangbingjian on 2016/5/20.
 */
public class CheckBankCardReq extends BaseReq {

    private String token;

    private String bankOwner;

    private String bankCode;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBankOwner() {
        return bankOwner;
    }

    public void setBankOwner(String bankOwner) {
        this.bankOwner = bankOwner;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }
}
