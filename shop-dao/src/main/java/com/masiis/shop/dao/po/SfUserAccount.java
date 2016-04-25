/*
 * SfUserAccount.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-04-08 Created
 */
package com.masiis.shop.dao.po;

import java.math.BigDecimal;
import java.util.Date;

public class SfUserAccount {

    private Long id;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 可提现额度
     */
    private BigDecimal extractableFee;
    /**
     * 已经申请提现金额
     */
    private BigDecimal appliedFee;
    /**
     * 结算中
     */
    private BigDecimal countingFee;
    /**
     * 乐观锁字段
     */
    private Long version;

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
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public BigDecimal getExtractableFee() {
        return extractableFee;
    }
    public void setExtractableFee(BigDecimal extractableFee) {
        this.extractableFee = extractableFee;
    }
    public BigDecimal getAppliedFee() {
        return appliedFee;
    }
    public void setAppliedFee(BigDecimal appliedFee) {
        this.appliedFee = appliedFee;
    }
    public BigDecimal getCountingFee() {
        return countingFee;
    }
    public void setCountingFee(BigDecimal countingFee) {
        this.countingFee = countingFee;
    }
    public Long getVersion() {
        return version;
    }
    public void setVersion(Long version) {
        this.version = version;
    }
}