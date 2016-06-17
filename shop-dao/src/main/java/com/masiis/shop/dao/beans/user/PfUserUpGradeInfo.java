package com.masiis.shop.dao.beans.user;

import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.PfUserUpgradeNotice;

/**
 * Created by JingHao on 2016/6/15
 * 升级管理业务属性
 */
public class PfUserUpGradeInfo {

    private PfUserUpgradeNotice pfUserUpgradeNotice;

    private String skuName;

    private String lowerUserName;//下级姓名

    private ComUser comUser;//当前用户信息

    private String orgLevelName;//原始等级

    private String wishLevelName;//希望等级

    private String createDate;//格式化时间

    private String statusValue;//状态value

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

    public ComUser getComUser() {
        return comUser;
    }

    public void setComUser(ComUser comUser) {
        this.comUser = comUser;
    }

    public String getLowerUserName() {
        return lowerUserName;
    }

    public void setLowerUserName(String lowerUserName) {
        this.lowerUserName = lowerUserName;
    }

    public String getOrgLevelName() {
        return orgLevelName;
    }

    public void setOrgLevelName(String orgLevelName) {
        this.orgLevelName = orgLevelName;
    }

    public String getWishLevelName() {
        return wishLevelName;
    }

    public void setWishLevelName(String wishLevelName) {
        this.wishLevelName = wishLevelName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getStatusValue() {
        return statusValue;
    }

    public void setStatusValue(String statusValue) {
        this.statusValue = statusValue;
    }
}
