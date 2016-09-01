package com.masiis.shop.api.bean.order;

import com.masiis.shop.api.bean.base.BaseBusinessReq;

/**
 * Created by hzz on 2016/8/19.
 */
public class BorderOffineReq extends BaseBusinessReq {

    private Long bOrderId;

    public Long getbOrderId() {
        return bOrderId;
    }

    public void setbOrderId(Long bOrderId) {
        this.bOrderId = bOrderId;
    }
}
