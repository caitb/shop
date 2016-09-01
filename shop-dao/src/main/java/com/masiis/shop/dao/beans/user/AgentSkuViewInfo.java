package com.masiis.shop.dao.beans.user;

import com.masiis.shop.dao.po.ComAgentLevel;
import com.masiis.shop.dao.po.ComSku;
import com.masiis.shop.dao.po.PfSkuAgent;

import java.math.BigDecimal;

/**
 *
 * @Date:2016/4/2
 * @Auth:lzh
 */
public class AgentSkuViewInfo {
    private String levelName;
    private Integer levelId;
    private BigDecimal agentFee;
    /**
     * sku销售单价
     */
    private BigDecimal retailFee;
    /**
     * skuName
     */
    private String skuName;
    private String skuUrl;
    private BigDecimal sinFee;
    private BigDecimal bailFee;
    private Integer isShow;
    private Integer quantity;
    private BigDecimal minProfit;
    private BigDecimal maxProfit;
    private BigDecimal minSingle;
    private BigDecimal maxSingle;
    private Integer isOrganization;
    private String organizationSuffix;
    private Integer agentNum;

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public BigDecimal getAgentFee() {
        return agentFee;
    }

    public void setAgentFee(BigDecimal agentFee) {
        this.agentFee = agentFee;
    }

    public BigDecimal getSinFee() {
        return sinFee;
    }

    public void setSinFee(BigDecimal sinFee) {
        this.sinFee = sinFee;
    }

    public BigDecimal getBailFee() {
        return bailFee;
    }

    public void setBailFee(BigDecimal bailFee) {
        this.bailFee = bailFee;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getRetailFee() {
        return retailFee;
    }

    public void setRetailFee(BigDecimal retailFee) {
        this.retailFee = retailFee;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public BigDecimal getMinProfit() {
        return minProfit;
    }

    public void setMinProfit(BigDecimal minProfit) {
        this.minProfit = minProfit;
    }

    public BigDecimal getMaxProfit() {
        return maxProfit;
    }

    public void setMaxProfit(BigDecimal maxProfit) {
        this.maxProfit = maxProfit;
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

    public Integer getIsOrganization() {
        return isOrganization;
    }

    public void setIsOrganization(Integer isOrganization) {
        this.isOrganization = isOrganization;
    }

    public String getOrganizationSuffix() {
        return organizationSuffix;
    }

    public void setOrganizationSuffix(String organizationSuffix) {
        this.organizationSuffix = organizationSuffix;
    }

    public Integer getAgentNum() {
        return agentNum;
    }

    public void setAgentNum(Integer agentNum) {
        this.agentNum = agentNum;
    }

    public String getSkuUrl() {
        return skuUrl;
    }

    public void setSkuUrl(String skuUrl) {
        this.skuUrl = skuUrl;
    }
}
