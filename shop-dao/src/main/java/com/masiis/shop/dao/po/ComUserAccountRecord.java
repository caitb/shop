/*
 * ComUserAccountRecord.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-23 Created
 */
package com.masiis.shop.dao.po;

import java.math.BigDecimal;
import java.util.Date;

public class ComUserAccountRecord {

    private Long id;
    /**
     * 用户id
     */
    private Long comUserId;
    /**
     * 用户资产账户id
     */
    private Long userAccountId;
    /**
     * 操作金额
     */
    private BigDecimal handleFee;
    /**
     * 此次记录执行前的钱
     */
    private BigDecimal prevFee;
    /**
     * 此次操作后的钱
     */
    private BigDecimal nextFee;
    /**
     * 操作资金类型: 0,总收入; 1,可提现; 
     */
    private Integer feeType;
    /**
     * 账单id
     */
    private Long billId;
    /**
     * 此次记录的时间
     */
    private Date handlerTime;
    /**
     * 此次记录操作人
     */
    private String handler;

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
    public Long getUserAccountId() {
        return userAccountId;
    }
    public void setUserAccountId(Long userAccountId) {
        this.userAccountId = userAccountId;
    }
    public BigDecimal getHandleFee() {
        return handleFee;
    }
    public void setHandleFee(BigDecimal handleFee) {
        this.handleFee = handleFee;
    }
    public BigDecimal getPrevFee() {
        return prevFee;
    }
    public void setPrevFee(BigDecimal prevFee) {
        this.prevFee = prevFee;
    }
    public BigDecimal getNextFee() {
        return nextFee;
    }
    public void setNextFee(BigDecimal nextFee) {
        this.nextFee = nextFee;
    }
    public Integer getFeeType() {
        return feeType;
    }
    public void setFeeType(Integer feeType) {
        this.feeType = feeType;
    }
    public Long getBillId() {
        return billId;
    }
    public void setBillId(Long billId) {
        this.billId = billId;
    }
    public Date getHandlerTime() {
        return handlerTime;
    }
    public void setHandlerTime(Date handlerTime) {
        this.handlerTime = handlerTime;
    }
    public String getHandler() {
        return handler;
    }
    public void setHandler(String handler) {
        this.handler = handler == null ? null : handler.trim();
    }
}