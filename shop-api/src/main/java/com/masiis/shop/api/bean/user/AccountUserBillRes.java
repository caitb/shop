package com.masiis.shop.api.bean.user;

import com.masiis.shop.api.bean.base.BaseRes;

import java.util.List;

/**
 * Created by wangbingjian on 2016/5/6.
 */
public class AccountUserBillRes extends BaseRes{

    private List<AccountUserBill> userBills;

    public List<AccountUserBill> getUserBills() {
        return userBills;
    }

    public void setUserBills(List<AccountUserBill> userBills) {
        this.userBills = userBills;
    }
}
