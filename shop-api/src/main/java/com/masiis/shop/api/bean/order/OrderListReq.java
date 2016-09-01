package com.masiis.shop.api.bean.order;

import com.masiis.shop.api.bean.base.BasePagingReq;

/**
 * Created by hw on 2016/8/3.
 */
public class OrderListReq extends BasePagingReq {

    private Integer orderStatus;//订单状态    0待付款，7待发货，3已完成，8已发货
    private Integer sendType;//发货方式   1平台发货，2店主发货

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getSendType() {
        return sendType;
    }

    public void setSendType(Integer sendType) {
        this.sendType = sendType;
    }
}
