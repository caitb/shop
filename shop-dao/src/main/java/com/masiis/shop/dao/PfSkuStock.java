/*
 * PfSkuStock.java
 * Copyright(C) 2014-2016 ��ʿ����
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao;

import java.util.Date;

/**
 * ƽ̨��ƷSKU����
 * 
 * @author masiis
 * @version 1.0 2016-03-02
 */
public class PfSkuStock {

    private Integer id;
    /**
     * ����ʱ��
     */
    private Date createTime;
    /**
     * ƽ̨��Ӧ��id(��ʱ���ã�����)
     */
    private Integer supplierId;
    /**
     * spu����id
     */
    private Integer spuId;
    /**
     * sku����id
     */
    private Integer skuId;
    /**
     * �����
     */
    private Integer stock;
    /**
     * ��������
     */
    private Integer frozenStock;
    /**
     * ��ע
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