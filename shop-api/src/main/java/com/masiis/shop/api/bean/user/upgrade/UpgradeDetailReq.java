package com.masiis.shop.api.bean.user.upgrade;

import com.masiis.shop.api.bean.base.BaseBusinessReq;

/**
 * 展示下级申请详情页
 * Created by wangbingjian on 2016/8/4.
 */
public class UpgradeDetailReq extends BaseBusinessReq {

    private Long upgradeId;

    public Long getUpgradeId() {
        return upgradeId;
    }

    public void setUpgradeId(Long upgradeId) {
        this.upgradeId = upgradeId;
    }
}
