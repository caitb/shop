package com.masiis.shop.api.bean.user;

import com.masiis.shop.api.bean.base.BasePagingRes;

import java.util.List;

/**
 * Created by wangbingjian on 2016/5/6.
 */
public class WithdrawRecordRes extends BasePagingRes {

    private boolean isLast = false;

    private List<WithdrawRecord> records;

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        isLast = last;
    }

    public List<WithdrawRecord> getRecords() {
        return records;
    }

    public void setRecords(List<WithdrawRecord> records) {
        this.records = records;
    }
}
