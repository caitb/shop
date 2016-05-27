package com.masiis.shop.api.bean.order;

import com.masiis.shop.api.bean.base.BasePagingReq;

/**
 * @Date 2016/5/5
 * @Auther lzh
 */
public class OrderListPagingReq extends BasePagingReq {
    private String token;
    private Integer orderStatus;
    private Integer index;
    private Integer sendType;
    private Long shopId;

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getSendType() {
        return sendType;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getIndex() {
        return index;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOstatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }
}
