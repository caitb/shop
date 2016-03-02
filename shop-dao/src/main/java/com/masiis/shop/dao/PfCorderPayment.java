/*
 * PfCorderPayment.java
 * Copyright(C) 2014-2016 ��ʿ����
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao;

import java.math.BigDecimal;
import java.util.Date;

/**
 * ƽ̨��������֧����
 * 
 * @author masiis
 * @version 1.0 2016-03-02
 */
public class PfCorderPayment {

    private Long id;
    /**
     * ����ʱ��
     */
    private Date createTime;
    /**
     * ������
     */
    private Long pfCorderId;
    /**
     * ֧�����
     */
    private BigDecimal amount;
    /**
     * ֧������id
     */
    private Integer payTypeId;
    /**
     * ֧����������
     */
    private String payTypeName;
    /**
     * �Ƿ���Ч(0��1��)
     */
    private Integer isEnabled;
    /**
     * ��ע
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