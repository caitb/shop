/*
 * PfUserBill.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-23 Created
 */
package com.masiis.shop.dao.po;

import java.math.BigDecimal;
import java.util.Date;

public class PfUserBill {

    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 创建人
     */
    private Long createMan;
    /**
     * 结算日期
     */
    private Date balanceDate;
    /**
     * 销售总金额
     */
    private BigDecimal totalAmount;
    /**
     * 账单结算总金额
     */
    private BigDecimal billAmount;
    /**
     * 退货总金额
     */
    private BigDecimal returnAmount;
    /**
     * 平台佣金
     */
    private String pfBrokerage;
    /**
     * 供应商id
     */
    private Long supplierId;
    /**
     * 结算开始日期
     */
    private Date countStartTime;
    /**
     * 结算结束日期
     */
    private Date countEndTime;
    /**
     * 账单状态0未结算1已结算
     */
    private Integer status;
    private String remark;

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
    public Date getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    public Long getCreateMan() {
        return createMan;
    }
    public void setCreateMan(Long createMan) {
        this.createMan = createMan;
    }
    public Date getBalanceDate() {
        return balanceDate;
    }
    public void setBalanceDate(Date balanceDate) {
        this.balanceDate = balanceDate;
    }
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
    public BigDecimal getBillAmount() {
        return billAmount;
    }
    public void setBillAmount(BigDecimal billAmount) {
        this.billAmount = billAmount;
    }
    public BigDecimal getReturnAmount() {
        return returnAmount;
    }
    public void setReturnAmount(BigDecimal returnAmount) {
        this.returnAmount = returnAmount;
    }
    public String getPfBrokerage() {
        return pfBrokerage;
    }
    public void setPfBrokerage(String pfBrokerage) {
        this.pfBrokerage = pfBrokerage == null ? null : pfBrokerage.trim();
    }
    public Long getSupplierId() {
        return supplierId;
    }
    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }
    public Date getCountStartTime() {
        return countStartTime;
    }
    public void setCountStartTime(Date countStartTime) {
        this.countStartTime = countStartTime;
    }
    public Date getCountEndTime() {
        return countEndTime;
    }
    public void setCountEndTime(Date countEndTime) {
        this.countEndTime = countEndTime;
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