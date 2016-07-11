/*
 * SfUserPromotionGift.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-07-04 Created
 */
package com.masiis.shop.dao.po;

public class SfUserPromotionGift {

    /**
     * 商城用户活动奖励商品主键id
     */
    private Integer id;
    /**
     * 活动id
     */
    private Integer promoId;
    /**
     * 活动规则id
     */
    private Integer promoRuleId;
    /**
     * 奖品类型(0:奖品1:优惠券)
     */
    private Integer type;
    /**
     * 奖励品id(根据奖励类型而定0:gift主键id)
     */
    private Integer giftValue;
    /**
     * 奖励数量
     */
    private Integer quantity;
    /**
     * 已发放奖励数量
     */
    private Integer promoQuantity;
    /**
     * 奖励数量上限(0为无限)
     */
    private Integer upperQuantity;
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
    public Integer getPromoId() {
        return promoId;
    }
    public void setPromoId(Integer promoId) {
        this.promoId = promoId;
    }
    public Integer getPromoRuleId() {
        return promoRuleId;
    }
    public void setPromoRuleId(Integer promoRuleId) {
        this.promoRuleId = promoRuleId;
    }
    public Integer getType() {
        return type;
    }
    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getGiftValue() {
        return giftValue;
    }

    public void setGiftValue(Integer giftValue) {
        this.giftValue = giftValue;
    }

    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public Integer getPromoQuantity() {
        return promoQuantity;
    }
    public void setPromoQuantity(Integer promoQuantity) {
        this.promoQuantity = promoQuantity;
    }
    public Integer getUpperQuantity() {
        return upperQuantity;
    }
    public void setUpperQuantity(Integer upperQuantity) {
        this.upperQuantity = upperQuantity;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}