/*
 * SfUserAccountRecord.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-04-10 Created
 */
package com.masiis.shop.dao.po;

import java.math.BigDecimal;
import java.util.Date;

public class SfUserAccountRecord {

    /**
     * 主键id
     */
    private Long id;
    /**
     * 用户id
     */
    private Long comUserId;
    /**
     * 小铺端用户资产账户id
     */
    private Long sfUserAccountId;
    /**
     * 流水号,重复的记录为回滚操作
     */
    private String handleSerialNum;
    /**
     * 操作金额数目
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
     * 操作资金类型: 0,订单入账结算中; 1,结算中减少; 2,用户提现 ; 3,可提现增加
     */
    private Integer feeType;
    /**
     * 来源单据id
     */
    private Long sourceId;
    /**
     * 流水操作类型: 0,正向逻辑操作; 1,流水回滚操作
     */
    private Integer handleType;
    /**
     * 此次记录的时间
     */
    private Date handleTime;
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
    public Long getSfUserAccountId() {
        return sfUserAccountId;
    }
    public void setSfUserAccountId(Long sfUserAccountId) {
        this.sfUserAccountId = sfUserAccountId;
    }
    public String getHandleSerialNum() {
        return handleSerialNum;
    }
    public void setHandleSerialNum(String handleSerialNum) {
        this.handleSerialNum = handleSerialNum == null ? null : handleSerialNum.trim();
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
    public Long getSourceId() {
        return sourceId;
    }
    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }
    public Integer getHandleType() {
        return handleType;
    }
    public void setHandleType(Integer handleType) {
        this.handleType = handleType;
    }
    public Date getHandleTime() {
        return handleTime;
    }
    public void setHandleTime(Date handleTime) {
        this.handleTime = handleTime;
    }
    public String getHandler() {
        return handler;
    }
    public void setHandler(String handler) {
        this.handler = handler == null ? null : handler.trim();
    }
}