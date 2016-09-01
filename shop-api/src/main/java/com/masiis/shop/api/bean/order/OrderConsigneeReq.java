package com.masiis.shop.api.bean.order;

import com.masiis.shop.api.bean.base.BaseBusinessReq;

/**
 * Created by hw on 2016/8/4.
 */
public class OrderConsigneeReq extends BaseBusinessReq {

    private Long orderId;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
