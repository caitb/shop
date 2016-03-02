/*
 * PfCorderPayment.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao.generate;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 平台分销订单支付表
 * 
 * @author masiis
 * @version 1.0 2016-03-02
 */
public class PfCorderPayment {

    private Long id;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 订单号
     */
    private Long pfCorderId;
    /**
     * 支付金额
     */
    private BigDecimal amount;
    /**
     * 支付类型id
     */
    private Integer payTypeId;
    /**
     * 支付类型名称
     */
    private String payTypeName;
    /**
     * 是否有效(0否1是)
     */
    private Integer isEnabled;
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
    public Long getPfCorderId() {
        return pfCorderId;
    }
    public void setPfCorderId(Long pfCorderId) {
        this.pfCorderId = pfCorderId;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public Integer getPayTypeId() {
        return payTypeId;
    }
    public void setPayTypeId(Integer payTypeId) {
        this.payTypeId = payTypeId;
    }
    public String getPayTypeName() {
        return payTypeName;
    }
    public void setPayTypeName(String payTypeName) {
        this.payTypeName = payTypeName == null ? null : payTypeName.trim();
    }
    public Integer getIsEnabled() {
        return isEnabled;
    }
    public void setIsEnabled(Integer isEnabled) {
        this.isEnabled = isEnabled;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}