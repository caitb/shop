package com.masiis.shop.api.bean.user;

import com.masiis.shop.api.bean.base.BaseReq;

/**
 * Created by wangbingjian on 2016/5/21.
 */
public class WithdrawConfirmReq extends BaseReq{

    private String token;

    private String money;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
