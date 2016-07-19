/*
 * PbStorageBillItem.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-07-19 Created
 */
package com.masiis.shop.dao.po;

import java.util.Date;

public class PbStorageBillItem {

    /**
     * 库存变更单子表id
     */
    private Integer id;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 库存变更单主表id
     */
    private Integer pbStorageBillId;
    /**
     * sku主键id
     */
    private Integer skuId;
    /**
     * sku名称
     */
    private String skuName;
    /**
     * 数量
     */
    private Integer quantity;
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
    public Integer getPbStorageBillId() {
        return pbStorageBillId;
    }
    public void setPbStorageBillId(Integer pbStorageBillId) {
        this.pbStorageBillId = pbStorageBillId;
    }
    public Integer getSkuId() {
        return skuId;
    }
    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }
    public String getSkuName() {
        return skuName;
    }
    public void setSkuName(String skuName) {
        this.skuName = skuName == null ? null : skuName.trim();
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