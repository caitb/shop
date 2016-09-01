package com.masiis.shop.api.bean.order;

import com.masiis.shop.api.bean.base.BasePagingReq;

/**
 * Created by hw on 2016/8/5.
 */
public class MyOrderListReq extends BasePagingReq {

    private Integer orderStatus;//订单状态    0待付款，7待发货，3已完成，6排单中，8已发货（待收货）

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }
}
