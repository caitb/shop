package com.masiis.shop.api.bean.order;

import com.masiis.shop.api.bean.base.BaseBusinessReq;

/**
 * Created by hw on 2016/8/5.
 */
public class OrderDetailReq extends BaseBusinessReq {

    private Long orderId;//订单id

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
