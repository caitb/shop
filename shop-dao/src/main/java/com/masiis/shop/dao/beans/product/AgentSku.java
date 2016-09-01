package com.masiis.shop.dao.beans.product;

import java.math.BigDecimal;

/**
 * Created by jiajinghao on 2016/8/17.
 */
public class AgentSku {

    private String brandName;//品牌介绍

    private String skuImgURl;//商品小图

    private BigDecimal price;//价格

    private String skuName;

    private Integer skuId;//skuId

    private Integer isAgent;//是否添加到仓库

    private Integer upgradeIsAgent;//推荐人的代理关系 0 未代理 1 代理

    private Integer upperIsAgent;//上级的代理状态 0 未代理 1 代理

    private Integer isBoss;//是否boss 0 否 1 是

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getSkuImgURl() {
        return skuImgURl;
    }

    public void setSkuImgURl(String skuImgURl) {
        this.skuImgURl = skuImgURl;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public Integer getIsAgent() {
        return isAgent;
    }

    public void setIsAgent(Integer isAgent) {
        this.isAgent = isAgent;
    }

    public Integer getUpgradeIsAgent() {
        return upgradeIsAgent;
    }

    public void setUpgradeIsAgent(Integer upgradeIsAgent) {
        this.upgradeIsAgent = upgradeIsAgent;
    }

    public Integer getUpperIsAgent() {
        return upperIsAgent;
    }

    public void setUpperIsAgent(Integer upperIsAgent) {
        this.upperIsAgent = upperIsAgent;
    }

    public Integer getIsBoss() {
        return isBoss;
    }

    public void setIsBoss(Integer isBoss) {
        this.isBoss = isBoss;
    }
}
