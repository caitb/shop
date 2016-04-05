/*
 * PfUserSkuStock.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-17 Created
 */
package com.masiis.shop.dao.po;

import java.util.Date;

public class PfUserSkuStock {

    private Long id;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 用户ID
     */
    private Long userId;
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
     * 自发货库存
     */
    private Integer customStock;
    /**
     * 乐观锁
     */
    private Integer version;

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
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Integer getCustomStock() {
        return customStock;
    }

    public void setCustomStock(Integer customStock) {
        this.customStock = customStock;
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
}