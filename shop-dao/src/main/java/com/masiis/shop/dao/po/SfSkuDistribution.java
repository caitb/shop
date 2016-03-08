/*
 * SfSkuDistribution.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-03 Created
 */
package com.masiis.shop.dao.po;

import java.math.BigDecimal;

/**
 * 商铺SKU分销分润表
 * 
 * @author masiis
 * @version 1.0 2016-03-03
 */
public class SfSkuDistribution {

    private Long id;
    /**
     * SKU主键ID
     */
    private Long skuId;
    /**
     * 从小到大对应分润
     */
    private Integer sort;
    /**
     * 折扣(例如0.35为35%也就是3.5折)
     */
    private BigDecimal discount;
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
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "SfSkuDistribution{" +
                "id=" + id +
                ", skuId=" + skuId +
                ", sort=" + sort +
                ", discount=" + discount +
                ", remark='" + remark + '\'' +
                '}';
    }
}