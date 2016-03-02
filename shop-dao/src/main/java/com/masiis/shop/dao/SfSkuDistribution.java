/*
 * SfSkuDistribution.java
 * Copyright(C) 2014-2016 ��ʿ����
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao;

import java.math.BigDecimal;

/**
 * ����SKU���������
 * 
 * @author masiis
 * @version 1.0 2016-03-02
 */
public class SfSkuDistribution {

    private Integer id;
    /**
     * SKU����ID
     */
    private Integer skuId;
    /**
     * ��С�����Ӧ����
     */
    private Integer sort;
    /**
     * �ۿ�(����0.35Ϊ35%Ҳ����3.5��)
     */
    private BigDecimal discount;
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
    public Integer getSort() {
        return sort;
    }
    public void setSort(Integer sort) {
        this.sort = sort;
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