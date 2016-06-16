/*
 * PfUserSkuTestlist.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-06-08 Created
 */
package com.masiis.shop.dao.po;

import java.util.Date;

public class PfUserSkuTestlist {

    private Long id;
    private Date createTime;
    private Long comUserId;
    private Integer skuId;
    /**
     * 是否启用: 0,不启用; 1,启用
     */
    private Integer isEnable;

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
    public Long getComUserId() {
        return comUserId;
    }
    public void setComUserId(Long comUserId) {
        this.comUserId = comUserId;
    }
    public Integer getSkuId() {
        return skuId;
    }
    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }
    public Integer getIsEnable() {
        return isEnable;
    }
    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }
}