package com.masiis.shop.api.bean.order;

import com.masiis.shop.api.bean.base.BasePagingReq;

/**
 * Created by hw on 2016/8/8.
 */
public class LowerLevelOrderReq extends BasePagingReq {

    private Integer orderStatus;
    private Long lowerId;

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Long getLowerId() {
        return lowerId;
    }

    public void setLowerId(Long lowerId) {
        this.lowerId = lowerId;
    }
}
