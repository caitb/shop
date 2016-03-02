/*
 * ComSku.java
 * Copyright(C) 2014-2016 ��ʿ����
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao;

import java.math.BigDecimal;
import java.util.Date;

/**
 * ��ƷSKU��
 * 
 * @author masiis
 * @version 1.0 2016-03-02
 */
public class ComSku {

    private Integer id;
    /**
     * sku����
     */
    private String name;
    /**
     * spu����id
     */
    private Integer spuId;
    /**
     * ����ʱ��
     */
    private Date createTime;
    /**
     * ������
     */
    private Long createMan;
    /**
     * �޸�ʱ��
     */
    private Date modifyTime;
    /**
     * �޸���
     */
    private Long modifyMan;
    /**
     * sku����
     */
    private String barCode;
    /**
     * �ɱ���
     */
    private BigDecimal priceCost;
    /**
     * �г���
     */
    private BigDecimal priceMarket;
    /**
     * ���ۼ�
     */
    private BigDecimal priceRetail;
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
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
    public Integer getSpuId() {
        return spuId;
    }
    public void setSpuId(Integer spuId) {
        this.spuId = spuId;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Long getCreateMan() {
        return createMan;
    }
    public void setCreateMan(Long createMan) {
        this.createMan = createMan;
    }
    public Date getModifyTime() {
        return modifyTime;
    }
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
    public Long getModifyMan() {
        return modifyMan;
    }
    public void setModifyMan(Long modifyMan) {
        this.modifyMan = modifyMan;
    }
    public String getBarCode() {
        return barCode;
    }
    public void setBarCode(String barCode) {
        this.barCode = barCode == null ? null : barCode.trim();
    }
    public BigDecimal getPriceCost() {
        return priceCost;
    }
    public void setPriceCost(BigDecimal priceCost) {
        this.priceCost = priceCost;
    }
    public BigDecimal getPriceMarket() {
        return priceMarket;
    }
    public void setPriceMarket(BigDecimal priceMarket) {
        this.priceMarket = priceMarket;
    }
    public BigDecimal getPriceRetail() {
        return priceRetail;
    }
    public void setPriceRetail(BigDecimal priceRetail) {
        this.priceRetail = priceRetail;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}