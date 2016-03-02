/*
 * ComAgentLevel.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 代理等级表
 * 
 * @author masiis
 * @version 1.0 2016-03-02
 */
public class ComAgentLevel {

    /**
     * 代理等级
     */
    private Integer id;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 代理等级名称
     */
    private String name;
    /**
     * 图片地址
     */
    private String imgUrl;
    /**
     * 拿货门槛
     */
    private BigDecimal lower;
    /**
     * 折扣
     */
    private BigDecimal discount;
    private String remark;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
    public String getImgUrl() {
        return imgUrl;
    }
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }
    public BigDecimal getLower() {
        return lower;
    }
    public void setLower(BigDecimal lower) {
        this.lower = lower;
    }
    public BigDecimal getDiscount() {
        return discount;
    }
    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}