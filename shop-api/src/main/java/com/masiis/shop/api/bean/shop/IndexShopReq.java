package com.masiis.shop.api.bean.shop;

import com.masiis.shop.api.bean.base.BasePagingReq;

/**
 * Created by hw on 2016/7/29.
 */
public class IndexShopReq extends BasePagingReq {

    private Long shopId;
    private Long fromUserId;

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Long fromUserId) {
        this.fromUserId = fromUserId;
    }

    @Override
    public String toString() {
        return "IndexShopReq{" +
                "shopId=" + shopId +
                ", fromUserId=" + fromUserId +
                '}';
    }
}
