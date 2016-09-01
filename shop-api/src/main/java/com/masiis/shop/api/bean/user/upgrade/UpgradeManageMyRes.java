package com.masiis.shop.api.bean.user.upgrade;

import com.masiis.shop.api.bean.base.BaseRes;
import com.masiis.shop.dao.beans.user.upgrade.UpgradeManagePo;

/**
 * 升级管理-Po
 * Created by wangbingjian on 2016/8/3.
 */
public class UpgradeManageMyRes extends BaseRes{

    private Integer auditStatus;

    private UpgradeManagePo upgradeManagePo;

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public UpgradeManagePo getUpgradeManagePo() {
        return upgradeManagePo;
    }

    public void setUpgradeManagePo(UpgradeManagePo upgradeManagePo) {
        this.upgradeManagePo = upgradeManagePo;
    }
}
