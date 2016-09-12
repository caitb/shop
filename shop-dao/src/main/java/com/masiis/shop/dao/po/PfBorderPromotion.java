/*
 * PfBorderPromotion.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-09-06 Created
 */
package com.masiis.shop.dao.po;

import java.math.BigDecimal;
import java.util.Date;

public class PfBorderPromotion {

    private Long id;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 订单id
     */
    private Long pfBorderId;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 用户上级id
     */
    private Long userPid;
    /**
     * 促销活动id(没有为0)
     */
    private Integer pfPromotionId;
    /**
     * spu主键id
     */
    private Integer spuId;
    /**
     * sku主键id
     */
    private Integer skuId;
    /**
     * sku名称
     */
    private String skuName;
    /**
     * 数量
     */
    private Integer quantity;
    /**
     * 购买价格
     */
    private BigDecimal unitPrice;
    /**
     * 总价
     */
    private BigDecimal totalPrice;
    /**
     * 是否已经发放赠品(0否1是)
     */
    private Integer isSend;
    /**
     * 是否支持拿货(0否1是)
     */
    private Integer isTake;
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
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Integer getPfPromotionId() {
        return pfPromotionId;
    }
    public void setPfPromotionId(Integer pfPromotionId) {
        this.pfPromotionId = pfPromotionId;
    }
    public Integer getSpuId() {
        return spuId;
    }
    public void setSpuId(Integer spuId) {
        this.spuId = spuId;
    }
    public Integer getSkuId() {
        return skuId;
    }
    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }
    public String getSkuName() {
        return skuName;
    }
    public void setSkuName(String skuName) {
        this.skuName = skuName == null ? null : skuName.trim();
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
    public Integer getIsSend() {
        return isSend;
    }
    public void setIsSend(Integer isSend) {
        this.isSend = isSend;
    }
    public Integer getIsTake() {
        return isTake;
    }
    public void setIsTake(Integer isTake) {
        this.isTake = isTake;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Long getUserPid() {
        return userPid;
    }

    public void setUserPid(Long userPid) {
        this.userPid = userPid;
    }
}