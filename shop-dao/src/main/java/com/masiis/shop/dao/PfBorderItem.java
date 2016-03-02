/*
 * PfBorderItem.java
 * Copyright(C) 2014-2016 ��ʿ����
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao;

import java.math.BigDecimal;
import java.util.Date;

/**
 * ƽ̨��������Ʒ�ӱ�
 * 
 * @author masiis
 * @version 1.0 2016-03-02
 */
public class PfBorderItem {

    private Long id;
    /**
     * ����ʱ��
     */
    private Date createTime;
    /**
     * ����id
     */
    private Long pfBorderId;
    /**
     * spu����id
     */
    private Integer spuId;
    /**
     * sku����id
     */
    private Integer skuId;
    /**
     * sku����
     */
    private String skuName;
    /**
     * ����
     */
    private Integer quantity;
    /**
     * ����
     */
    private BigDecimal unitPrice;
    /**
     * �ܼ�
     */
    private BigDecimal totalPrice;
    /**
     * ���۱�־λ(0δ����1������)
     */
    private Integer isComment;
    /**
     * �˻�����־λ(0δ�˻�1���˻�)
     */
    private Integer isReturn;
    private String remark;

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
    public Long getPfBorderId() {
        return pfBorderId;
    }
    public void setPfBorderId(Long pfBorderId) {
        this.pfBorderId = pfBorderId;
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
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
    public Integer getIsComment() {
        return isComment;
    }
    public void setIsComment(Integer isComment) {
        this.isComment = isComment;
    }
    public Integer getIsReturn() {
        return isReturn;
    }
    public void setIsReturn(Integer isReturn) {
        this.isReturn = isReturn;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}