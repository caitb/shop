package com.masiis.shop.api.bean.shop;

import com.masiis.shop.api.bean.base.BaseRes;

import java.math.BigDecimal;

/**
 * Created by hw on 2016/7/29.
 */
public class FreightRes extends BaseRes {

    private BigDecimal ownShipAmount;

    public BigDecimal getOwnShipAmount() {
        return ownShipAmount;
    }

    public void setOwnShipAmount(BigDecimal ownShipAmount) {
        this.ownShipAmount = ownShipAmount;
    }
}
