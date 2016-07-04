/*
 * SfUserPromotionRule.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-07-04 Created
 */
package com.masiis.shop.dao.po;

public class SfUserPromotionRule {

    /**
     * 商城用户活动规则主键id
     */
    private Integer id;
    /**
     * 活动id
     */
    private Integer promoId;
    /**
     * 活动类型(0:满赠)
     */
    private Integer type;
    /**
     * 规则数值(根据活动类型而定0:满足人数)
     */
    private Integer ruleValue;
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
    public Integer getType() {
        return type;
    }
    public void setType(Integer type) {
        this.type = type;
    }
    public Integer getRuleValue() {
        return ruleValue;
    }
    public void setRuleValue(Integer ruleValue) {
        this.ruleValue = ruleValue;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}