package com.masiis.shop.api.bean.user.upgrade;

import com.masiis.shop.api.bean.base.BaseBusinessRes;
import com.masiis.shop.dao.beans.user.upgrade.UpgradePackageInfo;

import java.util.List;


/**
 * Created by wangbingjian on 2016/8/5.
 */
public class UpGradePackageRes extends BaseBusinessRes {

    /**
     * 上级代理级别
     */
    private Integer upAgentLevel;

    private List<UpgradePackageInfo> upgradePackageInfo;

    public List<UpgradePackageInfo> getUpgradePackageInfo() {
        return upgradePackageInfo;
    }

    public void setUpgradePackageInfo(List<UpgradePackageInfo> upgradePackageInfo) {
        this.upgradePackageInfo = upgradePackageInfo;
    }

    public Integer getUpAgentLevel() {
        return upAgentLevel;
    }

    public void setUpAgentLevel(Integer upAgentLevel) {
        this.upAgentLevel = upAgentLevel;
    }
}
