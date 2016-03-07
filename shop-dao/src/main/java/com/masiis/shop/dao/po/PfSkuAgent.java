/*
 * PfSkuAgent.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-03 Created
 */
package com.masiis.shop.dao.po;

import java.math.BigDecimal;

/**
 * 平台sku代理设置表

 * 
 * @author masiis
 * @version 1.0 2016-03-03
 */
public class PfSkuAgent {

    private Long id;
    /**
     * SKU主键ID
     */
    private Long skuId;
    /**
     * 代理等级表ID
     */
    private Long agentLevelId;
    /**
     * 折扣(例如0.35为35%也就是3.5折)
     */
    private BigDecimal discount;
    /**
     * 拿货数量
     */
    private Integer quantity;
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long getAgentLevelId() {
        return agentLevelId;
    }

    public void setAgentLevelId(Long agentLevelId) {
        this.agentLevelId = agentLevelId;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "PfSkuAgent{" +
                "id=" + id +
                ", skuId=" + skuId +
                ", agentLevelId=" + agentLevelId +
                ", discount=" + discount +
                ", quantity=" + quantity +
                ", remark='" + remark + '\'' +
                '}';
    }
}