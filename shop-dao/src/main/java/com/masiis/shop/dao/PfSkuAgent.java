/*
 * PfSkuAgent.java
 * Copyright(C) 2014-2016 ��ʿ����
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao;

import java.math.BigDecimal;

/**
 * ƽ̨sku�������ñ�

 * 
 * @author masiis
 * @version 1.0 2016-03-02
 */
public class PfSkuAgent {

    private Integer id;
    /**
     * SKU����ID
     */
    private Integer skuId;
    /**
     * ����ȼ���ID
     */
    private Integer agentLevelId;
    /**
     * �ۿ�(����0.35Ϊ35%Ҳ����3.5��)
     */
    private BigDecimal discount;
    /**
     * �û�����
     */
    private Integer quantity;
    private String remark;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getSkuId() {
        return skuId;
    }
    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }
    public Integer getAgentLevelId() {
        return agentLevelId;
    }
    public void setAgentLevelId(Integer agentLevelId) {
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
        this.remark = remark == null ? null : remark.trim();
    }
}