package com.masiis.shop.api.bean.shop;

import com.masiis.shop.api.bean.base.BasePagingRes;

import java.util.List;

/**
 * Created by wangbingjian on 2016/5/24.
 */
public class DistributionRecordRes extends BasePagingRes {
    /**
     * 分销记录列表
     */
    private List<SfDistribution> sfDistributions;
    /**
     * 是否为最后一页
     */
    private boolean isLast = false;

    public List<SfDistribution> getSfDistributions() {
        return sfDistributions;
    }

    public void setSfDistributions(List<SfDistribution> sfDistributions) {
        this.sfDistributions = sfDistributions;
    }

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        isLast = last;
    }
}
