package com.masiis.shop.api.bean.product;

import com.masiis.shop.api.bean.base.BaseReq;

/**
 * Created by JingHao on 2016/5/19 0019.
 */
public class ApplyProReq extends BaseReq{

    private Long id;//pfUserSkuStockId

    private Long selectedAddressId;//地址id

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSelectedAddressId() {
        return selectedAddressId;
    }

    public void setSelectedAddressId(Long selectedAddressId) {
        this.selectedAddressId = selectedAddressId;
    }
}
