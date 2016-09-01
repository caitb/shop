package com.masiis.shop.api.bean.user.upgrade;

import com.masiis.shop.api.bean.base.BaseRes;
import com.masiis.shop.dao.beans.system.ComSkuSimple;
import com.masiis.shop.dao.beans.user.upgrade.UpgradeManagePo;

import java.util.List;
import java.util.Map;

/**
 * Created by wangbingjian on 2016/8/4.
 */
public class UpgradeManageRebateRes extends BaseRes {

    private Integer auditStatus;

    private List<ComSkuSimple> comSkuSimples;

    private Map<Integer, String> status;

    private UpgradeManagePo upgradeManagePo;

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public List<ComSkuSimple> getComSkuSimples() {
        return comSkuSimples;
    }

    public void setComSkuSimples(List<ComSkuSimple> comSkuSimples) {
        this.comSkuSimples = comSkuSimples;
    }

    public Map<Integer, String> getStatus() {
        return status;
    }

    public void setStatus(Map<Integer, String> status) {
        this.status = status;
    }

    public UpgradeManagePo getUpgradeManagePo() {
        return upgradeManagePo;
    }

    public void setUpgradeManagePo(UpgradeManagePo upgradeManagePo) {
        this.upgradeManagePo = upgradeManagePo;
    }
}
