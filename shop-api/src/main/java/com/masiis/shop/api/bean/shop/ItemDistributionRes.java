package com.masiis.shop.api.bean.shop;

import com.masiis.shop.api.bean.base.BasePagingRes;


/**
 * Created by wangbingjian on 2016/5/23.
 */
public class ItemDistributionRes extends BasePagingRes {

    private boolean Last = false;

    private DistributionRecord distributionRecord;

    public boolean isLast() {
        return Last;
    }

    public void setLast(boolean last) {
        Last = last;
    }

    public DistributionRecord getDistributionRecord() {
        return distributionRecord;
    }

    public void setDistributionRecord(DistributionRecord distributionRecord) {
        this.distributionRecord = distributionRecord;
    }
}
