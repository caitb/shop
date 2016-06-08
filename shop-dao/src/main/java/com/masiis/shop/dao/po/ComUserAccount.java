/*
 * ComUserAccount.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-30 Created
 */
package com.masiis.shop.dao.po;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

public class ComUserAccount {

    private NumberFormat rmbFormat = NumberFormat.getCurrencyInstance(Locale.CHINA);

    private Long id;
    /**
     * 用户id
     */
    private Long comUserId;
    /**
     * 总销售额
     */
    private BigDecimal totalIncomeFee;
    /**
     * 总成本
     */
    private BigDecimal costFee;
    /**
     * 总利润
     */
    private BigDecimal profitFee;
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
     * 代理结算中
     */
    private BigDecimal agentBillAmount;
    /**
     * 分销结算中
     */
    private BigDecimal distributionBillAmount;
    /**
     * 保证金
     */
    private BigDecimal bailFee;
    private Date createdTime;
    private Date changedTime;
    private String changedBy;
    private String viewCountingFee;
    private String viewAgentBillAmount;
    private String viewDistributionBillAmount;
    private String viewExtractableFee;
    private String viewAppliedFee;
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

    public BigDecimal getCostFee() {
        return costFee;
    }

    public void setCostFee(BigDecimal costFee) {
        this.costFee = costFee;
    }

    public BigDecimal getProfitFee() {
        return profitFee;
    }

    public void setProfitFee(BigDecimal profitFee) {
        this.profitFee = profitFee;
    }

    public BigDecimal getExtractableFee() {
        return extractableFee;
    }

    public void setExtractableFee(BigDecimal extractableFee) {
        this.extractableFee = extractableFee;
        setViewExtractableFee(rmbFormat.format(extractableFee));
    }

    public BigDecimal getAppliedFee() {
        return appliedFee;
    }

    public void setAppliedFee(BigDecimal appliedFee) {
        this.appliedFee = appliedFee;
        setViewAppliedFee(rmbFormat.format(appliedFee));
    }

    public BigDecimal getCountingFee() {
        return countingFee;
    }

    public void setCountingFee(BigDecimal countingFee) {
        this.countingFee = countingFee;
        setViewCountingFee(rmbFormat.format(countingFee));
    }

    public BigDecimal getAgentBillAmount() {
        return agentBillAmount;
    }

    public void setAgentBillAmount(BigDecimal agentBillAmount) {
        this.agentBillAmount = agentBillAmount == null?new BigDecimal(0):agentBillAmount;
        setViewAgentBillAmount(rmbFormat.format(this.agentBillAmount));
    }

    public BigDecimal getDistributionBillAmount() {
        return distributionBillAmount;
    }

    public void setDistributionBillAmount(BigDecimal distributionBillAmount) {
        this.distributionBillAmount = distributionBillAmount == null?new BigDecimal(0):distributionBillAmount;
        setViewDistributionBillAmount(rmbFormat.format(this.distributionBillAmount));
    }

    public BigDecimal getBailFee() {
        return bailFee;
    }

    public void setBailFee(BigDecimal bailFee) {
        this.bailFee = bailFee;
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
        this.changedBy = changedBy;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getViewCountingFee() {
        return viewCountingFee;
    }

    public void setViewCountingFee(String viewCountingFee) {
        this.viewCountingFee = viewCountingFee;
    }

    public String getViewAgentBillAmount() {
        return viewAgentBillAmount;
    }

    public void setViewAgentBillAmount(String viewAgentBillAmount) {
        this.viewAgentBillAmount = viewAgentBillAmount;
    }

    public String getViewDistributionBillAmount() {
        return viewDistributionBillAmount;
    }

    public void setViewDistributionBillAmount(String viewDistributionBillAmount) {
        this.viewDistributionBillAmount = viewDistributionBillAmount;
    }

    public String getViewExtractableFee() {
        return viewExtractableFee;
    }

    public void setViewExtractableFee(String viewExtractableFee) {
        this.viewExtractableFee = viewExtractableFee;
    }

    public String getViewAppliedFee() {
        return viewAppliedFee;
    }

    public void setViewAppliedFee(String viewAppliedFee) {
        this.viewAppliedFee = viewAppliedFee;
    }

    @Override
    public String toString() {
        return "ComUserAccount{" +
                "id=" + id +
                ", comUserId=" + comUserId +
                ", totalIncomeFee=" + totalIncomeFee +
                ", costFee=" + costFee +
                ", profitFee=" + profitFee +
                ", extractableFee=" + extractableFee +
                ", appliedFee=" + appliedFee +
                ", countingFee=" + countingFee +
                ", bailFee=" + bailFee +
                ", createdTime=" + createdTime +
                ", changedTime=" + changedTime +
                ", changedBy='" + changedBy + '\'' +
                ", version=" + version +
                '}';
    }
}