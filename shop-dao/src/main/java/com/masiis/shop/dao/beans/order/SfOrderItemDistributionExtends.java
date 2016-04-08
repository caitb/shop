package com.masiis.shop.dao.beans.order;

import java.util.Date;

/**
 * Created by wangbingjian on 2016/4/8.
 */
public class SfOrderItemDistributionExtends {

    /**
     * 微信昵称
     */
    private String nkName;

    /**
     * sku名称
     */
    private String skuName;

    /**
     * 订单创建时间
     */
    private Date orderTime;

    /**
     * skuid
     */
    private Long skuId;

    public String getNkName() {
        return nkName;
    }

    public void setNkName(String nkName) {
        this.nkName = nkName;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }
}
