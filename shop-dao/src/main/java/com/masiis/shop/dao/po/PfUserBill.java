/*
 * PfUserBill.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-22 Created
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
     * 账单金额
     */
    private BigDecimal billAmount;
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
    public BigDecimal getBillAmount() {
        return billAmount;
    }
    public void setBillAmount(BigDecimal billAmount) {
        this.billAmount = billAmount;
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