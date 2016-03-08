/*
 * ComSku.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-03 Created
 */
package com.masiis.shop.dao.po;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品SKU表
 * 
 * @author masiis
 * @version 1.0 2016-03-03
 */
public class ComSku {

    private Integer id;
    /**
     * sku名称
     */
    private String name;
    /**
     * spu主键id
     */
    private Integer spuId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建人
     */
    private Long createMan;
    /**
     * 修改时间
     */
    private Date modifyTime;
    /**
     * 修改人
     */
    private Long modifyMan;
    /**
     * sku条码
     */
    private String barCode;
    /**
     * 成本价
     */
    private BigDecimal priceCost;
    /**
     * 市场价
     */
    private BigDecimal priceMarket;
    /**
     * 销售价
     */
    private BigDecimal priceRetail;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        this.barCode = barCode;
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
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "ComSku{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", spuId=" + spuId +
                ", createTime=" + createTime +
                ", createMan=" + createMan +
                ", modifyTime=" + modifyTime +
                ", modifyMan=" + modifyMan +
                ", barCode='" + barCode + '\'' +
                ", priceCost=" + priceCost +
                ", priceMarket=" + priceMarket +
                ", priceRetail=" + priceRetail +
                ", remark='" + remark + '\'' +
                '}';
    }
}