package com.masiis.shop.dao.mallBeans;

import com.alibaba.fastjson.annotation.JSONField;
import com.masiis.shop.dao.po.ComSku;
import com.masiis.shop.dao.po.ComSkuImage;

import java.math.BigDecimal;

/**
 * Created by JingHao on 2016/4/8 0008.
 */
public class SkuInfo{

    private ComSku comSku;

    private String slogan;//一句话描述

    private Long saleNum;//总销售量

    private Long shareNum;//分享量

    private BigDecimal shipAmount;

    private Integer stock;

    private String content;

    private Integer isSale;

    private String comSkuImageUrl;

    private Long shopSkuId;

    private Integer isOwnShip;//发货类型

    private String flagSelf;//自己发货的标志：null：只有平台发货

    private String wxqrCode;//店铺二维码

    private String skuName;//商品名称

    private BigDecimal priceRetail;

    private Integer skuId;

    private BigDecimal ownShipAmount;//自己发货的运费

    public ComSku getComSku() {
        return comSku;
    }

    public void setComSku(ComSku comSku) {
        this.comSku = comSku;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
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

    public BigDecimal getShipAmount() {
        return shipAmount;
    }

    public void setShipAmount(BigDecimal shipAmount) {
        this.shipAmount = shipAmount;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getIsSale() {
        return isSale;
    }

    public void setIsSale(Integer isSale) {
        this.isSale = isSale;
    }

    public String getComSkuImageUrl() {
        return comSkuImageUrl;
    }

    public void setComSkuImageUrl(String comSkuImageUrl) {
        this.comSkuImageUrl = comSkuImageUrl;
    }

    public Long getShopSkuId() {
        return shopSkuId;
    }

    public void setShopSkuId(Long shopSkuId) {
        this.shopSkuId = shopSkuId;
    }

    public Integer getIsOwnShip() {
        return isOwnShip;
    }

    public void setIsOwnShip(Integer isOwnShip) {
        this.isOwnShip = isOwnShip;
    }

    public String getFlagSelf() {
        return flagSelf;
    }

    public void setFlagSelf(String flagSelf) {
        this.flagSelf = flagSelf;
    }

    public String getWxqrCode() {
        return wxqrCode;
    }

    public void setWxqrCode(String wxqrCode) {
        this.wxqrCode = wxqrCode;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public BigDecimal getPriceRetail() {
        return priceRetail;
    }

    public void setPriceRetail(BigDecimal priceRetail) {
        this.priceRetail = priceRetail;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public BigDecimal getOwnShipAmount() {
        return ownShipAmount;
    }

    public void setOwnShipAmount(BigDecimal ownShipAmount) {
        this.ownShipAmount = ownShipAmount;
    }
}
