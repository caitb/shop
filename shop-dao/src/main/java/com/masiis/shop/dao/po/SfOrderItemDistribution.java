/*
 * SfOrderItemDistribution.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-04-08 Created
 */
package com.masiis.shop.dao.po;

import com.masiis.shop.dao.beans.order.SfOrderItemDistributionExtends;

import java.math.BigDecimal;
import java.util.Date;

public class SfOrderItemDistribution extends SfOrderItemDistributionExtends {

    private Long id;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 小铺订单ID
     */
    private Long sfOrderId;
    /**
     * 小铺订单商品子表ID
     */
    private Long sfOrderItemId;
    /**
     * 分润人id
     */
    private Long userId;
    /**
     * 分润信息表id
     */
    private Integer sfSkuDistributionId;
    /**
     * 分润金额
     */
    private BigDecimal distributionAmount;
    /**
     * 是否入账(0未入账1已入账)
     */
    private Integer isCounting;
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
    public Long getSfOrderId() {
        return sfOrderId;
    }
    public void setSfOrderId(Long sfOrderId) {
        this.sfOrderId = sfOrderId;
    }
    public Long getSfOrderItemId() {
        return sfOrderItemId;
    }
    public void setSfOrderItemId(Long sfOrderItemId) {
        this.sfOrderItemId = sfOrderItemId;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Integer getSfSkuDistributionId() {
        return sfSkuDistributionId;
    }
    public void setSfSkuDistributionId(Integer sfSkuDistributionId) {
        this.sfSkuDistributionId = sfSkuDistributionId;
    }
    public BigDecimal getDistributionAmount() {
        return distributionAmount;
    }
    public void setDistributionAmount(BigDecimal distributionAmount) {
        this.distributionAmount = distributionAmount;
    }
    public Integer getIsCounting() {
        return isCounting;
    }
    public void setIsCounting(Integer isCounting) {
        this.isCounting = isCounting;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}