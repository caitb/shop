package com.masiis.shop.api.bean.order;

import com.masiis.shop.api.bean.base.BaseBusinessReq;

/**
 * Created by hzz on 2016/5/25.
 */
public class TrialApplyPayReq extends BaseBusinessReq {

    private Integer skuId;
    private Long addressId;
    private String reason;
    private Long pfCorderId;


    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Long getPfCorderId() {
        return pfCorderId;
    }

    public void setPfCorderId(Long pfCorderId) {
        this.pfCorderId = pfCorderId;
    }
}
