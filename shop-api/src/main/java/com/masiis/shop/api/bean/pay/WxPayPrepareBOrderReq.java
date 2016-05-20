package com.masiis.shop.api.bean.pay;

import com.masiis.shop.api.bean.base.BaseBusinessReq;

/**
 * @Date 2016/5/19
 * @Auther lzh
 */
public class WxPayPrepareBOrderReq extends BaseBusinessReq {
    private Long orderId;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
