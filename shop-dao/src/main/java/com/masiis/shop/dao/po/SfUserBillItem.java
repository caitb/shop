/*
 * SfUserBillItem.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-04-12 Created
 */
package com.masiis.shop.dao.po;

import java.math.BigDecimal;
import java.util.Date;

public class SfUserBillItem {

    /**
     * 主键id
     */
    private Long id;
    /**
     * 用户id
     */
    private Long comUserId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建人
     */
    private Long createMan;
    /**
     * 账单id
     */
    private Long sfUserBillId;
    /**
     * 来源单据id
     */
    private Long sourceId;
    /**
     * 子项类型
     */
    private Integer itemType;
    /**
     * 子项子类型
     */
    private Integer itemSubType;
    /**
     * 来源单据创建时间
     */
    private Date sourceCreateTime;
    /**
     * 子项金额
     */
    private BigDecimal amount;
    /**
     * 是否结算: 0,未结算; 1,已结算
     */
    private Integer isCount;
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
    public Long getComUserId() {
        return comUserId;
    }
    public void setComUserId(Long comUserId) {
        this.comUserId = comUserId;
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
    public Long getSfUserBillId() {
        return sfUserBillId;
    }
    public void setSfUserBillId(Long sfUserBillId) {
        this.sfUserBillId = sfUserBillId;
    }
    public Long getSourceId() {
        return sourceId;
    }
    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }
    public Integer getItemType() {
        return itemType;
    }
    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }
    public Integer getItemSubType() {
        return itemSubType;
    }
    public void setItemSubType(Integer itemSubType) {
        this.itemSubType = itemSubType;
    }
    public Date getSourceCreateTime() {
        return sourceCreateTime;
    }
    public void setSourceCreateTime(Date sourceCreateTime) {
        this.sourceCreateTime = sourceCreateTime;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getIsCount() {
        return isCount;
    }

    public void setIsCount(Integer isCount) {
        this.isCount = isCount;
    }

    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}