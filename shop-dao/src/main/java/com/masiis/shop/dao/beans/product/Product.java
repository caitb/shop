package com.masiis.shop.dao.beans.product;

import com.masiis.shop.dao.po.ComSku;
import com.masiis.shop.dao.po.ComSkuImage;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by ZhaoLiang on 2016/3/2.
 */
public class Product extends ComSku {

    private String spuName;

    private String content;//图文详情

    private String slogan;

    private Integer stock;//商品库存

    private List<ComSkuImage> comSkuImages;//商品图

    private Boolean isPartner = false;//是否为合伙人

    private Integer agentLevel;//代理商级别

    private BigDecimal discount;//代理商折扣

    private Integer quantity;//数量

    public String getSpuName() {
        return spuName;
    }

    public void setSpuName(String spuName) {
        this.spuName = spuName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public List<ComSkuImage> getComSkuImages() {
        return comSkuImages;
    }

    public void setComSkuImages(List<ComSkuImage> comSkuImages) {
        this.comSkuImages = comSkuImages;
    }

    public Boolean getIsPartner() {
        return isPartner;
    }

    public void setIsPartner(Boolean isPartner) {
        this.isPartner = isPartner;
    }

    public Integer getAgentLevel() {
        return agentLevel;
    }

    public void setAgentLevel(Integer agentLevel) {
        this.agentLevel = agentLevel;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
