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
    private Long extractWay;
    /**
     * 银行卡图片相对地址
     */
    private String cardImg;
    /**
     * 是否是默认设置: 0,默认; 1非默认
     */
    private Integer isDefault;
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
        this.bankCard = bankCard;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getDepositBankName() {
        return depositBankName;
    }

    public void setDepositBankName(String depositBankName) {
        this.depositBankName = depositBankName;
    }

    public String getCardOwnerName() {
        return cardOwnerName;
    }

    public void setCardOwnerName(String cardOwnerName) {
        this.cardOwnerName = cardOwnerName;
    }

    public Long getComUserId() {
        return comUserId;
    }

    public void setComUserId(Long comUserId) {
        this.comUserId = comUserId;
    }

    public Long getExtractWay() {
        return extractWay;
    }

    public void setExtractWay(Long extractWay) {
        this.extractWay = extractWay;
    }

    public String getCardImg() {
        return cardImg;
    }

    public void setCardImg(String cardImg) {
        this.cardImg = cardImg;
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
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
        this.changedBy = changedBy;
    }

    @Override
    public String toString() {
        return "ComUserExtractwayInfo{" +
                "id=" + id +
                ", bankCard='" + bankCard + '\'' +
                ", bankName='" + bankName + '\'' +
                ", depositBankName='" + depositBankName + '\'' +
                ", cardOwnerName='" + cardOwnerName + '\'' +
                ", comUserId=" + comUserId +
                ", extractWay=" + extractWay +
                ", cardImg='" + cardImg + '\'' +
                ", isDefault=" + isDefault +
                ", isEnable=" + isEnable +
                ", createdTime=" + createdTime +
                ", changedTime=" + changedTime +
                ", changedBy='" + changedBy + '\'' +
                '}';
    }
}