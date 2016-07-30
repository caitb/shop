package com.masiis.shop.dao.beans.promotion;

import com.masiis.shop.dao.po.SfUserTurnTableRecord;

/**
 * Created by hzz on 2016/7/30.
 */
public class UserTurnTableRecordInfo extends SfUserTurnTableRecord {

    private String turnTableGiftName;
    private String statusName;
    private String createTimeString;


    public String getTurnTableGiftName() {
        return turnTableGiftName;
    }

    public void setTurnTableGiftName(String turnTableGiftName) {
        this.turnTableGiftName = turnTableGiftName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getCreateTimeString() {
        return createTimeString;
    }

    public void setCreateTimeString(String createTimeString) {
        this.createTimeString = createTimeString;
    }
}
