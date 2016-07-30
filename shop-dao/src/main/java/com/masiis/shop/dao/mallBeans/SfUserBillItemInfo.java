package com.masiis.shop.dao.mallBeans;

import com.masiis.shop.dao.po.SfUserBillItem;

import java.util.List;

/**
 * Created by jiajinghao on 2016/7/28.
 */
public class SfUserBillItemInfo {

    private SfUserBillItem sfUserBillItem;//分销记录

    private String userNameForBill;

    public SfUserBillItem getSfUserBillItem() {
        return sfUserBillItem;
    }

    public void setSfUserBillItem(SfUserBillItem sfUserBillItem) {
        this.sfUserBillItem = sfUserBillItem;
    }

    public String getUserNameForBill() {
        return userNameForBill;
    }

    public void setUserNameForBill(String userNameForBill) {
        this.userNameForBill = userNameForBill;
    }
}
