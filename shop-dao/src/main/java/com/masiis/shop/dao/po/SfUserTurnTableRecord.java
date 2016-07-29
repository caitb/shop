/*
 * SfUserTurnTableRecord.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-07-29 Created
 */
package com.masiis.shop.dao.po;

import java.util.Date;

public class SfUserTurnTableRecord {

    /**
     * 用户抽奖记录主键id
     */
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 转盘id
     */
    private Integer turnTableId;
    /**
     * 转盘奖品id
     */
    private Integer turnTableGiftId;
    /**
     * 订单id
     */
    private Long gorderId;
    /**
     * 状态(0:未领取1:已领取)
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
    public Integer getTurnTableGiftId() {
        return turnTableGiftId;
    }
    public void setTurnTableGiftId(Integer turnTableGiftId) {
        this.turnTableGiftId = turnTableGiftId;
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
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}