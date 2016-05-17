package com.masiis.shop.dao.mallBeans;

import java.math.BigDecimal;

/**
 * 小铺商品
 * @author muchaofeng
 * @date 2016/4/8 19:33
 */

public class SfShopDetail {
    private Integer skuId;
    private String skuName;//商品名
    private String skuAssia;//商品别名
    private String skuUrl;//商品首页地址
    private BigDecimal priceRetail;//销售价
    private String slogan;
    /**
     * 运费，0为包邮
     */
    private BigDecimal shipAmount;
    /**
     * 代理等级名
     */
    private String agentLevelName;
    private String icon;//等级标志
    /**
     * 保证金
     */
    private BigDecimal bail;

    public void setSkuAssia(String skuAssia) {
        this.skuAssia = skuAssia;
    }

    public String getSkuAssia() {
        return skuAssia;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getSlogan() {
        return slogan;
    }

    public BigDecimal getBail() {
        return bail;
    }

    public BigDecimal getPriceRetail() {
        return priceRetail;
    }

    public BigDecimal getShipAmount() {
        return shipAmount;
    }

    public String getAgentLevelName() {
        return agentLevelName;
    }

    public String getIcon() {
        return icon;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setAgentLevelName(String agentLevelName) {
        this.agentLevelName = agentLevelName;
    }

    public void setBail(BigDecimal bail) {
        this.bail = bail;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setPriceRetail(BigDecimal priceRetail) {
        this.priceRetail = priceRetail;
    }

    public void setShipAmount(BigDecimal shipAmount) {
        this.shipAmount = shipAmount;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public void setSkuUrl(String skuUrl) {
        this.skuUrl = skuUrl;
    }

    public String getSkuUrl() {
        return skuUrl;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }
}
