/*
 * PfUserRelation.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-04-18 Created
 */
package com.masiis.shop.dao.po;

import java.util.Date;

public class PfUserRelation {

    private Long id;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * sku主键id
     */
    private Integer skuId;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 上一级用户id
     */
    private Long userPid;
    /**
     * 是否可用
     */
    private Integer isEnable;
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
    public Integer getSkuId() {
        return skuId;
    }
    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Long getUserPid() {
        return userPid;
    }
    public void setUserPid(Long userPid) {
        this.userPid = userPid;
    }
    public Integer getIsEnable() {
        return isEnable;
    }
    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}