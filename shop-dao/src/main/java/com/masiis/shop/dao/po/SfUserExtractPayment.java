/*
 * SfUserExtractPayment.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-04-10 Created
 */
package com.masiis.shop.dao.po;

import java.math.BigDecimal;
import java.util.Date;

public class SfUserExtractPayment {

    /**
     * 主键id
     */
    private Long id;
    /**
     * 提现申请记录id
     */
    private Long extractApplyId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 系统流水号,微信打款商户订单号
     */
    private String extractSerialNum;
    /**
     * 1,微信; 2,支付宝; 3,银行卡
     */
    private Integer extractWay;
    /**
     * 微信订单号
     */
    private String outOrderId;
    /**
     * 打款金额
     */
    private BigDecimal amont;
    /**
     * 是否成功: 0,成功; 1,失败
     */
    private Integer isSuccess;
    /**
     * 微信支付时间
     */
    private Date payTime;
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
    public Long getExtractApplyId() {
        return extractApplyId;
    }
    public void setExtractApplyId(Long extractApplyId) {
        this.extractApplyId = extractApplyId;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public String getExtractSerialNum() {
        return extractSerialNum;
    }
    public void setExtractSerialNum(String extractSerialNum) {
        this.extractSerialNum = extractSerialNum == null ? null : extractSerialNum.trim();
    }
    public Integer getExtractWay() {
        return extractWay;
    }
    public void setExtractWay(Integer extractWay) {
        this.extractWay = extractWay;
    }
    public String getOutOrderId() {
        return outOrderId;
    }
    public void setOutOrderId(String outOrderId) {
        this.outOrderId = outOrderId == null ? null : outOrderId.trim();
    }
    public BigDecimal getAmont() {
        return amont;
    }
    public void setAmont(BigDecimal amont) {
        this.amont = amont;
    }
    public Integer getIsSuccess() {
        return isSuccess;
    }
    public void setIsSuccess(Integer isSuccess) {
        this.isSuccess = isSuccess;
    }
    public Date getPayTime() {
        return payTime;
    }
    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}