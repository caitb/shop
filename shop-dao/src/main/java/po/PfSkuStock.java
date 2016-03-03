/*
 * PfSkuStock.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-03 Created
 */
package com.masiis.shop.dao.po;

import java.util.Date;

/**
 * 平台商品SKU库存表
 * 
 * @author masiis
 * @version 1.0 2016-03-03
 */
public class PfSkuStock {

    private Integer id;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 平台供应商id(暂时不用，保留)
     */
    private Integer supplierId;
    /**
     * spu主键id
     */
    private Integer spuId;
    /**
     * sku主键id
     */
    private Integer skuId;
    /**
     * 库存量
     */
    private Integer stock;
    /**
     * 冻结库存量
     */
    private Integer frozenStock;
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
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Integer getSupplierId() {
        return supplierId;
    }
    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
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
    public Integer getStock() {
        return stock;
    }
    public void setStock(Integer stock) {
        this.stock = stock;
    }
    public Integer getFrozenStock() {
        return frozenStock;
    }
    public void setFrozenStock(Integer frozenStock) {
        this.frozenStock = frozenStock;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}