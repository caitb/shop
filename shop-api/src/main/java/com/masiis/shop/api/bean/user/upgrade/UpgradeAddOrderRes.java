package com.masiis.shop.api.bean.user.upgrade;

import com.masiis.shop.api.bean.base.BaseBusinessRes;

/**
 * Created by wangbingjian on 2016/9/7.
 */
public class UpgradeAddOrderRes extends BaseBusinessRes {

    private Long orderId;

    private Integer upgradeType;//0 0元升级， 1 非0元升级

    public Integer getUpgradeType() {
        return upgradeType;
    }

    public void setUpgradeType(Integer upgradeType) {
        this.upgradeType = upgradeType;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
