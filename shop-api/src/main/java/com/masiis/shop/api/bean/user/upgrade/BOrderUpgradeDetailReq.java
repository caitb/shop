package com.masiis.shop.api.bean.user.upgrade;

import com.masiis.shop.api.bean.base.BaseBusinessReq;

/**
 * Created by hzz on 2016/8/16.
 */
public class BOrderUpgradeDetailReq extends BaseBusinessReq {

    private Long pfBorderId;        //订单id
    private Long upgradeNoticeId;  //通知单id

    public Long getPfBorderId() {
        return pfBorderId;
    }

    public void setPfBorderId(Long pfBorderId) {
        this.pfBorderId = pfBorderId;
    }

    public Long getUpgradeNoticeId() {
        return upgradeNoticeId;
    }

    public void setUpgradeNoticeId(Long upgradeNoticeId) {
        this.upgradeNoticeId = upgradeNoticeId;
    }
}
