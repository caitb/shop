/*
 * PfSupplierBank.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-04-25 Created
 */
package com.masiis.shop.dao.po;

import java.util.Date;

public class PfSupplierBank {

    private Integer id;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 是否是默认
     */
    private Integer isDefault;
    /**
     * 平台供应商id(暂时不用，保留)
     */
    private Integer supplierId;
    /**
     * 开户行
     */
    private String bankName;
    /**
     * 开户名
     */
    private String accountName;
    /**
     * 卡号
     */
    private String cardNumber;
    /**
     * 备注
     */
    private String remark;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Integer getIsDefault() {
        return isDefault;
    }
    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }
    public Integer getSupplierId() {
        return supplierId;
    }
    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }
    public String getBankName() {
        return bankName;
    }
    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }
    public String getAccountName() {
        return accountName;
    }
    public void setAccountName(String accountName) {
        this.accountName = accountName == null ? null : accountName.trim();
    }
    public String getCardNumber() {
        return cardNumber;
    }
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber == null ? null : cardNumber.trim();
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}