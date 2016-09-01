package com.masiis.shop.api.bean.product;

import com.masiis.shop.api.bean.base.BaseBusinessReq;

/**
 * @Date 2016/8/19
 * @Author lzh
 */
public class SupplementSuccessReq extends BaseBusinessReq {
    private Long orderId;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
