/*
 * SfUserTurnTableRecord.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-07-30 Created
 */
package com.masiis.shop.dao.po;

import java.util.Date;

public class SfUserTurnTableRecord {

    /**
     * 用户抽奖记录主键id
     */
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
     * 用户id
     */
    private Long userId;
    /**
     * 转盘id
     */
    private Integer turnTableId;
    /**
     * 奖品id
     */
    private Integer giftId;
    /**
     * 订单id
     */
    private Long gorderId;
    /**
     * 状态(0:未领取1:已领取)
     */
    private Integer status;
    /**
     * 更新时间
     */
    private Date updateTime;
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
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Integer getTurnTableId() {
        return turnTableId;
    }
    public void setTurnTableId(Integer turnTableId) {
        this.turnTableId = turnTableId;
    }

    public Integer getGiftId() {
        return giftId;
    }

    public void setGiftId(Integer giftId) {
        this.giftId = giftId;
    }

    public Long getGorderId() {
        return gorderId;
    }
    public void setGorderId(Long gorderId) {
        this.gorderId = gorderId;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public Date getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}