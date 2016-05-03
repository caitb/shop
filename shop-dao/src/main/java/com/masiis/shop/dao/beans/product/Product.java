package com.masiis.shop.dao.beans.product;

import com.masiis.shop.dao.po.ComSku;
import com.masiis.shop.dao.po.ComSkuImage;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by JingHao on 2016/3/2.
 */
public class Product extends ComSku {

    private String spuName;

    private String content;//图文详情

    private String slogan;

    private Integer stock;//商品库存

    private List<ComSkuImage> comSkuImages;//商品图

    private Boolean isPartner = false;//是否为合伙人

    private Integer agentLevel;//代理商级别

    private Integer maxDiscount;//最高利润

    private BigDecimal shipAmount;//快递费用

    private Integer agentNum;//代理人数

    private ComSkuImage comSkuImage;

    private Integer pfuId;

    private Integer sendType;//发货方式

    private String brand;//品牌介绍

    private String logoUrl;//logo

    private Integer upperStock;//上级库存

    private Integer customStock;//自拿货库存

    private Integer isQueue;//排单flag

    private Integer isTrial;//是否可试用

    private Integer frozenStock;

    private String policy;//商业政策

    private Long userPid;//上级

    private Integer version;

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

    public Integer getMaxDiscount() {
        return maxDiscount;
    }

    public void setMaxDiscount(Integer maxDiscount) {
        this.maxDiscount = maxDiscount;
    }

    public BigDecimal getShipAmount() {
        return shipAmount;
    }

    public void setShipAmount(BigDecimal shipAmount) {
        this.shipAmount = shipAmount;
    }

    public Integer getAgentNum() {
        return agentNum;
    }

    public void setAgentNum(Integer agentNum) {
        this.agentNum = agentNum;
    }

    public ComSkuImage getComSkuImage() {
        return comSkuImage;
    }

    public void setComSkuImage(ComSkuImage comSkuImage) {
        this.comSkuImage = comSkuImage;
    }

    public Integer getPfuId() {
        return pfuId;
    }

    public void setPfuId(Integer pfuId) {
        this.pfuId = pfuId;
    }

    public Integer getSendType() {
        return sendType;
    }

    public void setSendType(Integer sendType) {
        this.sendType = sendType;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public Integer getUpperStock() {
        return upperStock;
    }

    public void setUpperStock(Integer upperStock) {
        this.upperStock = upperStock;
    }

    public Integer getCustomStock() {
        return customStock;
    }

    public void setCustomStock(Integer customStock) {
        this.customStock = customStock;
    }

    public Integer getIsQueue() {
        return isQueue;
    }

    public void setIsQueue(Integer isQueue) {
        this.isQueue = isQueue;
    }

    public Integer getIsTrial() {
        return isTrial;
    }

    public void setIsTrial(Integer isTrial) {
        this.isTrial = isTrial;
    }

    public Integer getFrozenStock() {
        return frozenStock;
    }

    public void setFrozenStock(Integer frozenStock) {
        this.frozenStock = frozenStock;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public Long getUserPid() {
        return userPid;
    }

    public void setUserPid(Long userPid) {
        this.userPid = userPid;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
