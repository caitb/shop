package com.masiis.shop.dao.mallBeans;

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

    private ComSkuImage comSkuImage;

    private Long shopSkuId;

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

    public ComSkuImage getComSkuImage() {
        return comSkuImage;
    }

    public void setComSkuImage(ComSkuImage comSkuImage) {
        this.comSkuImage = comSkuImage;
    }

    public Long getShopSkuId() {
        return shopSkuId;
    }

    public void setShopSkuId(Long shopSkuId) {
        this.shopSkuId = shopSkuId;
    }
}
