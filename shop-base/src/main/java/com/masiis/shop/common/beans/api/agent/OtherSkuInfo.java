package com.masiis.shop.common.beans.api.agent;

import java.math.BigDecimal;

/**
 * @Date 2016/8/11
 * @Author lzh
 */
public class OtherSkuInfo {
    /**
     * 商品名称
     */
    private String skuName;
    /**
     * 销售价
     */
    private BigDecimal retailFee;
    /**
     * 拿货价
     */
    private BigDecimal sinFee;
    private BigDecimal minSingle;
    private BigDecimal maxSingle;

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public BigDecimal getRetailFee() {
        return retailFee;
    }

    public void setRetailFee(BigDecimal retailFee) {
        this.retailFee = retailFee;
    }

    public BigDecimal getSinFee() {
        return sinFee;
    }

    public void setSinFee(BigDecimal sinFee) {
        this.sinFee = sinFee;
    }

    public BigDecimal getMinSingle() {
        return minSingle;
    }

    public void setMinSingle(BigDecimal minSingle) {
        this.minSingle = minSingle;
    }

    public BigDecimal getMaxSingle() {
        return maxSingle;
    }

    public void setMaxSingle(BigDecimal maxSingle) {
        this.maxSingle = maxSingle;
    }
}
