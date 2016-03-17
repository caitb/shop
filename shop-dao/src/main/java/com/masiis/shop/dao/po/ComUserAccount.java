/*
 * ComUserAccount.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-17 Created
 */
package com.masiis.shop.dao.po;

import java.math.BigDecimal;
import java.util.Date;

public class ComUserAccount {

    private Long id;
    /**
     * 用户id
     */
    private Long comUserId;
    /**
     * 交易额
     */
    private BigDecimal transactionFee;
    /**
     * 可提现额度
     */
    private BigDecimal extractableFee;
    /**
     * 结算中
     */
    private BigDecimal countingFee;
    /**
     * 当前资产总额
     */
    private BigDecimal nowTotalFee;
    private Date createdTime;
    private Date changedTime;
    private String changedBy;

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
    public BigDecimal getTransactionFee() {
        return transactionFee;
    }
    public void setTransactionFee(BigDecimal transactionFee) {
        this.transactionFee = transactionFee;
    }
    public BigDecimal getExtractableFee() {
        return extractableFee;
    }
    public void setExtractableFee(BigDecimal extractableFee) {
        this.extractableFee = extractableFee;
    }
    public BigDecimal getCountingFee() {
        return countingFee;
    }
    public void setCountingFee(BigDecimal countingFee) {
        this.countingFee = countingFee;
    }
    public BigDecimal getNowTotalFee() {
        return nowTotalFee;
    }
    public void setNowTotalFee(BigDecimal nowTotalFee) {
        this.nowTotalFee = nowTotalFee;
    }
    public Date getCreatedTime() {
        return createdTime;
    }
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
    public Date getChangedTime() {
        return changedTime;
    }
    public void setChangedTime(Date changedTime) {
        this.changedTime = changedTime;
    }
    public String getChangedBy() {
        return changedBy;
    }
    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy == null ? null : changedBy.trim();
    }
}