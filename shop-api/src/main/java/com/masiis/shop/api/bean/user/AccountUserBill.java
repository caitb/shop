package com.masiis.shop.api.bean.user;

import java.util.Date;

/**
 * Created by wangbingjian on 2016/5/6.
 */
public class AccountUserBill {
    /**
     * 结算日期
     */
    private Date balanceDate;
    /**
     * 账单结算总金额
     */
    private String billAmount;

    public Date getBalanceDate() {
        return balanceDate;
    }

    public void setBalanceDate(Date balanceDate) {
        this.balanceDate = balanceDate;
    }

    public String getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(String billAmount) {
        this.billAmount = billAmount;
    }
}
