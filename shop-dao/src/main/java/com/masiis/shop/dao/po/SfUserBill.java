/*
 * SfUserBill.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-04-12 Created
 */
package com.masiis.shop.dao.po;

import java.math.BigDecimal;
import java.util.Date;

public class SfUserBill {

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
     * 创建人id
     */
    private Long createMan;
    /**
     * 结算日期
     */
    private Date countingDate;
    /**
     * 账单金额
     */
    private BigDecimal billAmount;
    /**
     * 结算金额
     */
    private BigDecimal countAmount;
    /**
     * 退款金额
     */
    private BigDecimal returnAmount;
    /**
     * 来源单据开始时间
     */
    private Date sourceStartTime;
    /**
     * 来源单据结束时间
     */
    private Date sourceEndTime;
    /**
     * 账单状态0未结算1已结算
     */
    private Integer status;
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
    public Date getCountingDate() {
        return countingDate;
    }
    public void setCountingDate(Date countingDate) {
        this.countingDate = countingDate;
    }
    public BigDecimal getBillAmount() {
        return billAmount;
    }
    public void setBillAmount(BigDecimal billAmount) {
        this.billAmount = billAmount;
    }
    public BigDecimal getCountAmount() {
        return countAmount;
    }
    public void setCountAmount(BigDecimal countAmount) {
        this.countAmount = countAmount;
    }
    public BigDecimal getReturnAmount() {
        return returnAmount;
    }
    public void setReturnAmount(BigDecimal returnAmount) {
        this.returnAmount = returnAmount;
    }
    public Date getSourceStartTime() {
        return sourceStartTime;
    }
    public void setSourceStartTime(Date sourceStartTime) {
        this.sourceStartTime = sourceStartTime;
    }
    public Date getSourceEndTime() {
        return sourceEndTime;
    }
    public void setSourceEndTime(Date sourceEndTime) {
        this.sourceEndTime = sourceEndTime;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}