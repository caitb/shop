package com.masiis.shop.api.bean.user;

import com.masiis.shop.dao.po.ComSku;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Date 2016/5/4
 * @Auther lzh
 */
public class MarketProItem {
    private BigDecimal shipAmount;//试用费用
    private Integer skuId;//sku主键id
    private Integer isTrial;//是否允许试用
    private Integer isPay;//是否付款
    private String imgUrl;//图片地址
    private String discountLevel;//优惠区间
    private String bailLevel;//优惠区间
    private Integer maxDiscount;//优惠%
    private String skuName;
    private String skuPriceRetail;
    private Integer agentNum;//代理人数
    private Integer isPartner ;//是否为合伙人

    public BigDecimal getShipAmount() {
        return shipAmount;
    }

    public void setShipAmount(BigDecimal shipAmount) {
        this.shipAmount = shipAmount;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public Integer getIsTrial() {
        return isTrial;
    }

    public void setIsTrial(Integer isTrial) {
        this.isTrial = isTrial;
    }

    public Integer getIsPay() {
        return isPay;
    }

    public void setIsPay(Integer isPay) {
        this.isPay = isPay;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDiscountLevel() {
        return discountLevel;
    }

    public void setDiscountLevel(String discountLevel) {
        this.discountLevel = discountLevel;
    }

    public String getBailLevel() {
        return bailLevel;
    }

    public void setBailLevel(String bailLevel) {
        this.bailLevel = bailLevel;
    }

    public Integer getMaxDiscount() {
        return maxDiscount;
    }

    public void setMaxDiscount(Integer maxDiscount) {
        this.maxDiscount = maxDiscount;
    }

    public Integer getAgentNum() {
        return agentNum;
    }

    public void setAgentNum(Integer agentNum) {
        this.agentNum = agentNum;
    }

    public Integer getIsPartner() {
        return isPartner;
    }

    public void setIsPartner(Integer isPartner) {
        this.isPartner = isPartner;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getSkuPriceRetail() {
        return skuPriceRetail;
    }

    public void setSkuPriceRetail(String skuPriceRetail) {
        this.skuPriceRetail = skuPriceRetail;
    }
}
