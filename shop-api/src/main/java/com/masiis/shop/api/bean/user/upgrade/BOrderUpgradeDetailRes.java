package com.masiis.shop.api.bean.user.upgrade;

import com.masiis.shop.api.bean.base.BaseBusinessRes;
import com.masiis.shop.dao.beans.order.BOrderUpgradeDetail;


/**
 * Created by hzz on 2016/8/16.
 */
public class BOrderUpgradeDetailRes extends BaseBusinessRes {

    private BOrderUpgradeDetail bOrderUpgradeDetail;

    public BOrderUpgradeDetail getbOrderUpgradeDetail() {
        return bOrderUpgradeDetail;
    }

    public void setbOrderUpgradeDetail(BOrderUpgradeDetail bOrderUpgradeDetail) {
        this.bOrderUpgradeDetail = bOrderUpgradeDetail;
    }
}
