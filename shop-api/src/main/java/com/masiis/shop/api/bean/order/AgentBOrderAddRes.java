package com.masiis.shop.api.bean.order;

import com.masiis.shop.api.bean.base.BaseBusinessRes;

/**
 * @Date 2016/8/13
 * @Author lzh
 */
public class AgentBOrderAddRes extends BaseBusinessRes {
    private Long orderId;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}