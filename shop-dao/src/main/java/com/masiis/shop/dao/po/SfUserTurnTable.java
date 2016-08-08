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
     * 更新时间
     */
    private Date updateTime;
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