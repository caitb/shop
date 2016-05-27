/*
 * SfShopBillItem.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-05-26 Created
 */
package com.masiis.shop.dao.po;

import java.math.BigDecimal;
import java.util.Date;

public class SfShopBillItem {

    private Long id;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 用户id
     */
    private Long comUserId;
    /**
     * 店铺id
     */
    private Long shopId;
    /**
     * 店铺账单id
     */
    private Long sfShopBillId;
    /**
     * 来源单据id
     */
    private Long sourceId;
    /**
     * 店铺账单子项类型: 1,店铺分销订单销售; 2,店铺分销订单退货
     */
    private Integer itemType;
    /**
     * 来源单据创建时间
     */
    private Date sourceCreateTime;
    /**
     * 子项金额
     */
    private BigDecimal amount;
    /**
     * 是否结算: 0,未结算; 1,已结算
     */
    private Integer isCount;
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
    public Long getComUserId() {
        return comUserId;
    }
    public void setComUserId(Long comUserId) {
        this.comUserId = comUserId;
    }
    public Long getShopId() {
        return shopId;
    }
    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }
    public Long getSfShopBillId() {
        return sfShopBillId;
    }
    public void setSfShopBillId(Long sfShopBillId) {
        this.sfShopBillId = sfShopBillId;
    }
    public Long getSourceId() {
        return sourceId;
    }
    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }
    public Integer getItemType() {
        return itemType;
    }
    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }
    public Date getSourceCreateTime() {
        return sourceCreateTime;
    }
    public void setSourceCreateTime(Date sourceCreateTime) {
        this.sourceCreateTime = sourceCreateTime;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public Integer getIsCount() {
        return isCount;
    }
    public void setIsCount(Integer isCount) {
        this.isCount = isCount;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}