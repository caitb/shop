/*
 * SfUserShareParam.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-05-07 Created
 */
package com.masiis.shop.dao.po;

import java.util.Date;

public class SfUserShareParam {

    private Long id;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 分享人
     */
    private Long fUserId;
    /**
     * 小铺id
     */
    private Long shopId;
    /**
     * skuID
     */
    private Long skuId;
    /**
     * 备注
     */
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
    public Long getfUserId() {
        return fUserId;
    }
    public void setfUserId(Long fUserId) {
        this.fUserId = fUserId;
    }
    public Long getShopId() {
        return shopId;
    }
    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }
    public Long getSkuId() {
        return skuId;
    }
    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}