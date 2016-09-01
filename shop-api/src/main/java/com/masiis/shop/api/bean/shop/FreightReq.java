package com.masiis.shop.api.bean.shop;

import com.masiis.shop.api.bean.base.BasePagingReq;

/**
 * Created by hw on 2016/7/29.
 */
public class FreightReq extends BasePagingReq {
    private String shipAmount;

    public String getShipAmount() {
        return shipAmount;
    }

    public void setShipAmount(String shipAmount) {
        this.shipAmount = shipAmount;
    }
}
