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
     * 总销售额
     */
    private BigDecimal totalIncomeFee;
    private BigDecimal costFee;
    private BigDecimal bailFee;
    /**
     * 可提现额度
     */
    private BigDecimal extractableFee;
    /**
     * 结算中
     */
    private BigDecimal countingFee;
    private Date createdTime;
    private Date changedTime;
    private String changedBy;
    private Long version;

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

    public BigDecimal getTotalIncomeFee() {
        return totalIncomeFee;
    }

    public void setTotalIncomeFee(BigDecimal totalIncomeFee) {
        this.totalIncomeFee = totalIncomeFee;
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

    public BigDecimal getCostFee() {
        return costFee;
    }

    public void setCostFee(BigDecimal costFee) {
        this.costFee = costFee;
    }

    public BigDecimal getBailFee() {
        return bailFee;
    }

    public void setBailFee(BigDecimal bailFee) {
        this.bailFee = bailFee;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}