/*
 * PfSkuStockLog.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-05-13 Created
 */
package com.masiis.shop.dao.po;

import java.util.Date;

public class PfSkuStockLog {

    private Long id;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 平台库存表id
     */
    private Long pfSkuStockId;
    /**
     * 商品spuid
     */
    private Integer spuId;
    /**
     * 商品skuid
     */
    private Integer skuId;
    /**
     * 变更前数量
     */
    private Integer prevStock;
    /**
     * 变更后数量
     */
    private Integer nextStock;
    /**
     * 0新加入合伙1补货
     */
    private Integer type;
    /**
     * 来源单据id
     */
    private Long billId;
    /**
     * 备注
     */
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
    public Long getPfSkuStockId() {
        return pfSkuStockId;
    }
    public void setPfSkuStockId(Long pfSkuStockId) {
        this.pfSkuStockId = pfSkuStockId;
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
    public Integer getPrevStock() {
        return prevStock;
    }
    public void setPrevStock(Integer prevStock) {
        this.prevStock = prevStock;
    }
    public Integer getNextStock() {
        return nextStock;
    }
    public void setNextStock(Integer nextStock) {
        this.nextStock = nextStock;
    }
    public Integer getType() {
        return type;
    }
    public void setType(Integer type) {
        this.type = type;
    }
    public Long getBillId() {
        return billId;
    }
    public void setBillId(Long billId) {
        this.billId = billId;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}