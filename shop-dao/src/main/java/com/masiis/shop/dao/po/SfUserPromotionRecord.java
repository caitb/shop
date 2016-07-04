/*
 * SfUserPromotionRecord.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-07-04 Created
 */
package com.masiis.shop.dao.po;

import java.util.Date;

public class SfUserPromotionRecord {

    private Long id;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建人
     */
    private Long createMan;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 活动id
     */
    private Integer promoId;
    /**
     * 活动规则id
     */
    private Integer promoRuleId;
    /**
     * 状态(0为参与1已参与)
     */
    private Integer status;
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
    public Long getCreateMan() {
        return createMan;
    }
    public void setCreateMan(Long createMan) {
        this.createMan = createMan;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
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
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}