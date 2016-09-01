package com.masiis.shop.api.bean.order;

import com.masiis.shop.api.bean.base.BaseBusinessReq;

/**
 * @Date 2016/8/16
 * @Author lzh
 */
public class AgentApplySuccessReq extends BaseBusinessReq {
    private Long orderId;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
