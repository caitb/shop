/*
 * ComUserExtractwayInfo.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-17 Created
 */
package com.masiis.shop.dao.po;

import java.util.Date;

public class ComUserExtractwayInfo {

    private Long id;
    /**
     * 银行卡号
     */
    private String bankCard;
    /**
     * 银行名称
     */
    private String bankName;
    /**
     * 开户行名称
     */
    private String depositBankName;
    /**
     * 持卡人姓名
     */
    private String cardOwnerName;
    /**
     * 用户id
     */
    private Long comUserId;
    /**
     * 提现方式
     */
    private Integer extractWay;
    /**
     * 是否启用:0,启用; 1,未启用
     */
    private Integer isEnable;
    private Date createdTime;
    private Date changedTime;
    private String changedBy;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getBankCard() {
        return bankCard;
    }
    public void setBankCard(String bankCard) {
        this.bankCard = bankCard == null ? null : bankCard.trim();
    }
    public String getBankName() {
        return bankName;
    }
    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }
    public String getDepositBankName() {
        return depositBankName;
    }
    public void setDepositBankName(String depositBankName) {
        this.depositBankName = depositBankName == null ? null : depositBankName.trim();
    }
    public String getCardOwnerName() {
        return cardOwnerName;
    }
    public void setCardOwnerName(String cardOwnerName) {
        this.cardOwnerName = cardOwnerName == null ? null : cardOwnerName.trim();
    }
    public Long getComUserId() {
        return comUserId;
    }
    public void setComUserId(Long comUserId) {
        this.comUserId = comUserId;
    }
    public Integer getExtractWay() {
        return extractWay;
    }
    public void setExtractWay(Integer extractWay) {
        this.extractWay = extractWay;
    }
    public Integer getIsEnable() {
        return isEnable;
    }
    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
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