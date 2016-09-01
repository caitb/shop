package com.masiis.shop.api.bean.order;

import com.masiis.shop.api.bean.base.BaseBusinessReq;

/**
 * Created by hw on 2016/8/8.
 */
public class PfBorderCashierdeskReq extends BaseBusinessReq {

    private Long orderId;
//    private Long skuId;

//    public Long getSkuId() {
//        return skuId;
//    }
//
//    public void setSkuId(Long skuId) {
//        this.skuId = skuId;
//    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

}
