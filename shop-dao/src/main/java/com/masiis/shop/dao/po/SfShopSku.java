/*
 * SfShopSku.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-04-08 Created
 */
package com.masiis.shop.dao.po;

import com.masiis.shop.dao.beans.product.SfShopSkuExtends;

import java.math.BigDecimal;
import java.util.Date;

public class SfShopSku extends SfShopSkuExtends {

    private Long id;
    private Date createTime;
    /**
     * 小铺id
     */
    private Long shopId;
    /**
     * 小铺归属人id
     */
    private Long shopUserId;
    /**
     * spu主键id
     */
    private Integer spuId;
    /**
     * sku主键id
     */
    private Integer skuId;
    /**
     * 上下架标志(0下架1上架)
     */
    private Integer isSale;
    /**
     * 代理等级id
     */
    private Integer agentLevelId;
    /**
     * 保证金
     */
    private BigDecimal bail;
    /**
     * 运费，0为包邮
     */
    private BigDecimal shipAmount;
    /**
     * 总销售量
     */
    private Long saleNum;
    /**
     * 分享量
     */
    private Long shareNum;
    /**
     * 二维码图片全称(不包括路径)
     */
    private String qrCode;
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
    public Long getShopId() {
        return shopId;
    }
    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }
    public Long getShopUserId() {
        return shopUserId;
    }
    public void setShopUserId(Long shopUserId) {
        this.shopUserId = shopUserId;
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
    public Integer getIsSale() {
        return isSale;
    }
    public void setIsSale(Integer isSale) {
        this.isSale = isSale;
    }
    public Integer getAgentLevelId() {
        return agentLevelId;
    }
    public void setAgentLevelId(Integer agentLevelId) {
        this.agentLevelId = agentLevelId;
    }
    public BigDecimal getBail() {
        return bail;
    }
    public void setBail(BigDecimal bail) {
        this.bail = bail;
    }
    public BigDecimal getShipAmount() {
        return shipAmount;
    }
    public void setShipAmount(BigDecimal shipAmount) {
        this.shipAmount = shipAmount;
    }
    public Long getSaleNum() {
        return saleNum;
    }
    public void setSaleNum(Long saleNum) {
        this.saleNum = saleNum;
    }
    public Long getShareNum() {
        return shareNum;
    }
    public void setShareNum(Long shareNum) {
        this.shareNum = shareNum;
    }
    public String getQrCode() {
        return qrCode;
    }
    public void setQrCode(String qrCode) {
        this.qrCode = qrCode == null ? null : qrCode.trim();
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}