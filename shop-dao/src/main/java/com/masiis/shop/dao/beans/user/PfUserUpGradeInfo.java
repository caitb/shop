package com.masiis.shop.dao.beans.user;

import com.masiis.shop.dao.po.PfUserUpgradeNotice;

/**
 * Created by JingHao on 2016/6/15
 * 升级管理业务属性
 */
public class PfUserUpGradeInfo {

    private PfUserUpgradeNotice pfUserUpgradeNotice;

    private String skuName;


    public PfUserUpgradeNotice getPfUserUpgradeNotice() {
        return pfUserUpgradeNotice;
    }

    public void setPfUserUpgradeNotice(PfUserUpgradeNotice pfUserUpgradeNotice) {
        this.pfUserUpgradeNotice = pfUserUpgradeNotice;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }
}
