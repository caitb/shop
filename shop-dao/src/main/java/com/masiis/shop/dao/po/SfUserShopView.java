/*
 * SfUserShopView.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-04-08 Created
 */
package com.masiis.shop.dao.po;

import java.util.Date;

public class SfUserShopView {

    private Long id;
    private Date createTime;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 小铺id
     */
    private Long shopId;
    /**
     * 小铺归属人id
     */
    private Long shopUserId;
    private String remark;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Long getShopId() {
        return shopId;
    }
    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }
    public Long getShopUserId() {
        return shopUserId;
    }
    public void setShopUserId(Long shopUserId) {
        this.shopUserId = shopUserId;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}