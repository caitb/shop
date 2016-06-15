/*
 * PfBorderRecommenReward.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-06-14 Created
 */
package com.masiis.shop.dao.po;

import java.math.BigDecimal;
import java.util.Date;

public class PfBorderRecommenReward {

    private Long id;
    private Date createTime;
    /**
     * 订单id
     */
    private Long pfBorderId;
    /**
     * 订单子表id
     */
    private Long pfBorderItemId;
    /**
     * skuId
     */
    private Integer skuId;
    /**
     * 推荐人id
     */
    private Long recommenUserId;
    /**
     * 数量
     */
    private Integer quantity;
    /**
     * 奖励单价
     */
    private BigDecimal rewardUnitPrice;
    /**
     * 奖励总金额
     */
    private BigDecimal rewardTotalPrice;
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
    public Long getPfBorderId() {
        return pfBorderId;
    }
    public void setPfBorderId(Long pfBorderId) {
        this.pfBorderId = pfBorderId;
    }
    public Long getPfBorderItemId() {
        return pfBorderItemId;
    }
    public void setPfBorderItemId(Long pfBorderItemId) {
        this.pfBorderItemId = pfBorderItemId;
    }
    public Integer getSkuId() {
        return skuId;
    }
    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }
    public Long getRecommenUserId() {
        return recommenUserId;
    }
    public void setRecommenUserId(Long recommenUserId) {
        this.recommenUserId = recommenUserId;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public BigDecimal getRewardUnitPrice() {
        return rewardUnitPrice;
    }
    public void setRewardUnitPrice(BigDecimal rewardUnitPrice) {
        this.rewardUnitPrice = rewardUnitPrice;
    }
    public BigDecimal getRewardTotalPrice() {
        return rewardTotalPrice;
    }
    public void setRewardTotalPrice(BigDecimal rewardTotalPrice) {
        this.rewardTotalPrice = rewardTotalPrice;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}