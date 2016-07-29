/*
 * SfUserTurnTableItem.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-07-29 Created
 */
package com.masiis.shop.dao.po;

import java.util.Date;

public class SfUserTurnTableItem {

    /**
     * 用户转盘明细表主键id
     */
    private Long id;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 用户转盘id
     */
    private Long userTurnTableId;
    /**
     * 转盘id
     */
    private Integer turnTableId;
    /**
     * 规则id
     */
    private Integer turnTableRuleId;
    /**
     * 类型(0:增加1:减少)
     */
    private Integer type;
    /**
     * 次数
     */
    private Integer times;
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
    public Long getUserTurnTableId() {
        return userTurnTableId;
    }
    public void setUserTurnTableId(Long userTurnTableId) {
        this.userTurnTableId = userTurnTableId;
    }
    public Integer getTurnTableId() {
        return turnTableId;
    }
    public void setTurnTableId(Integer turnTableId) {
        this.turnTableId = turnTableId;
    }
    public Integer getTurnTableRuleId() {
        return turnTableRuleId;
    }
    public void setTurnTableRuleId(Integer turnTableRuleId) {
        this.turnTableRuleId = turnTableRuleId;
    }
    public Integer getType() {
        return type;
    }
    public void setType(Integer type) {
        this.type = type;
    }
    public Integer getTimes() {
        return times;
    }
    public void setTimes(Integer times) {
        this.times = times;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}