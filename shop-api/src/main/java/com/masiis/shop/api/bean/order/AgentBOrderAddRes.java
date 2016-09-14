package com.masiis.shop.api.bean.order;

import com.masiis.shop.api.bean.base.BaseBusinessRes;

/**
 * @Date 2016/8/13
 * @Author lzh
 */
public class AgentBOrderAddRes extends BaseBusinessRes {
    private Long orderId;
    private Integer payType;//0 0元免支付, 1 需要正常支付

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
