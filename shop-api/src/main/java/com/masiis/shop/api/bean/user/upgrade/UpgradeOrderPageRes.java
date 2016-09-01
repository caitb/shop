package com.masiis.shop.api.bean.user.upgrade;

import com.masiis.shop.api.bean.base.BaseBusinessRes;
import com.masiis.shop.dao.beans.order.BOrderUpgradeDetail;

/**
 * Created by wangbingjian on 2016/8/5.
 */
public class UpgradeOrderPageRes extends BaseBusinessRes {

    private BOrderUpgradeDetail upgradeDetail;
    /**
     * 支付逾期日期
     */
    private String payDate;

    public BOrderUpgradeDetail getUpgradeDetail() {
        return upgradeDetail;
    }

    public void setUpgradeDetail(BOrderUpgradeDetail upgradeDetail) {
        this.upgradeDetail = upgradeDetail;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }
}
