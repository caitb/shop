package com.masiis.shop.dao.beans.user.upgrade;

import java.math.BigDecimal;

/**
 * Created by wangbingjian on 2016/8/5.
 */
public class UpgradePackageInfo {

    /**
     * 总价
     */
    private BigDecimal totalPrice;
    /**
     * 单价
     */
    private BigDecimal unitPrice;
    /**
     * 最低利润
     */
    private BigDecimal lowProfit;
    /**
     * 最高利润
     */
    private BigDecimal highProfit;
    /**
     * 数量
     */
    private Long quantity;
    /**
     * 保证金
     */
    private BigDecimal bailAmount;
    /**
     * 零售价
     */
    private BigDecimal priceRetail;
    /**
     * 等级人数
     */
    private Integer levelCount;
    /**
     * 是否可以创建组织 0：不可以  1：可以
     */
    private Integer isOrganization;
    /**
     * 创建组织后缀
     */
    private String organizationSuffix;
    /**
     * 代理等级
     */
    private Integer agentLevel;
    /**
     * 代理名称
     */
    private String agentName;

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getLowProfit() {
        return lowProfit;
    }

    public void setLowProfit(BigDecimal lowProfit) {
        this.lowProfit = lowProfit;
    }

    public BigDecimal getHighProfit() {
        return highProfit;
    }

    public void setHighProfit(BigDecimal highProfit) {
        this.highProfit = highProfit;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getBailAmount() {
        return bailAmount;
    }

    public void setBailAmount(BigDecimal bailAmount) {
        this.bailAmount = bailAmount;
    }

    public BigDecimal getPriceRetail() {
        return priceRetail;
    }

    public void setPriceRetail(BigDecimal priceRetail) {
        this.priceRetail = priceRetail;
    }

    public Integer getLevelCount() {
        return levelCount;
    }

    public void setLevelCount(Integer levelCount) {
        this.levelCount = levelCount;
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

    public Integer getAgentLevel() {
        return agentLevel;
    }

    public void setAgentLevel(Integer agentLevel) {
        this.agentLevel = agentLevel;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }
}
