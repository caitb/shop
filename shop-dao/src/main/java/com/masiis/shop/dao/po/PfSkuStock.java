/*
 * PfSkuStock.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-17 Created
 */
package com.masiis.shop.dao.po;

import java.util.Date;

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
    /**
     * 是否进入排单
     */
    private Integer isQueue;
    /**
     * 版本号
     */
    private Integer version;

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
    public Integer getVersion() {
        return version;
    }
    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getIsQueue() {
        return isQueue;
    }

    public void setIsQueue(Integer isQueue) {
        this.isQueue = isQueue;
    }

    @Override
    public String toString() {
        return "PfSkuStock{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", supplierId=" + supplierId +
                ", spuId=" + spuId +
                ", skuId=" + skuId +
                ", stock=" + stock +
                ", frozenStock=" + frozenStock +
                ", remark='" + remark + '\'' +
                ", isQueue=" + isQueue +
                ", version=" + version +
                '}';
    }
}