/*
 * SfUserTurnTable.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-07-29 Created
 */
package com.masiis.shop.dao.po;

import java.util.Date;

public class SfUserTurnTable {

    /**
     * 用户转盘信息主键id
     */
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 转盘表id
     */
    private Integer turnTableId;
    /**
     * 未抽奖的次数
     */
    private Integer notUsedTimes;
    /**
     * 已抽奖的次数
     */
    private Integer usedTimes;
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
    public Integer getNotUsedTimes() {
        return notUsedTimes;
    }
    public void setNotUsedTimes(Integer notUsedTimes) {
        this.notUsedTimes = notUsedTimes;
    }
    public Integer getUsedTimes() {
        return usedTimes;
    }
    public void setUsedTimes(Integer usedTimes) {
        this.usedTimes = usedTimes;
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