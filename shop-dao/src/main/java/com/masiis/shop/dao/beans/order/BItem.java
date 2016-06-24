package com.masiis.shop.dao.beans.order;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by cai_tb on 16/6/24.
 */
public class BItem {

    private Integer     skuId;
    private BigDecimal  originalPrice;
    private BigDecimal  unitPrice;
    private BigDecimal  totalPrice;
    private BigDecimal  bailAmount;
    private Integer     quantity;
    private String      skuName;

    private Map<String, String> imgUrls;

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
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

    public BigDecimal getBailAmount() {
        return bailAmount;
    }

    public void setBailAmount(BigDecimal bailAmount) {
        this.bailAmount = bailAmount;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public Map<String, String> getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(Map<String, String> imgUrls) {
        this.imgUrls = imgUrls;
    }

    @Override
    public String toString() {
        return "BItem{" +
                "skuId=" + skuId +
                ", originalPrice=" + originalPrice +
                ", unitPrice=" + unitPrice +
                ", totalPrice=" + totalPrice +
                ", bailAmount=" + bailAmount +
                ", quantity=" + quantity +
                ", skuName='" + skuName + '\'' +
                ", imgUrls=" + imgUrls +
                '}';
    }
}
