/*
 * SfGorderOperationLog.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-07-04 Created
 */
package com.masiis.shop.dao.po;

import java.util.Date;

public class SfGorderOperationLog {

    private Long id;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建人
     */
    private Long createMan;
    /**
     * 活动订单id
     */
    private Long sfGorderId;
    /**
     * 活动订单状态
     */
    private Integer sfOrderStatus;
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
    public Long getCreateMan() {
        return createMan;
    }
    public void setCreateMan(Long createMan) {
        this.createMan = createMan;
    }
    public Long getSfGorderId() {
        return sfGorderId;
    }
    public void setSfGorderId(Long sfGorderId) {
        this.sfGorderId = sfGorderId;
    }
    public Integer getSfOrderStatus() {
        return sfOrderStatus;
    }
    public void setSfOrderStatus(Integer sfOrderStatus) {
        this.sfOrderStatus = sfOrderStatus;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}