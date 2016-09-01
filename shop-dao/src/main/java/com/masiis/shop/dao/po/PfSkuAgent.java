/*
 * PfSkuAgent.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-05-24 Created
 */
package com.masiis.shop.dao.po;

import java.math.BigDecimal;

public class PfSkuAgent {

    private Integer id;
    /**
     * SKU主键ID
     */
    private Integer skuId;
    /**
     * 代理等级表ID
     */
    private Integer agentLevelId;
    /**
     * 等级标志
     */
    private String icon;
    /**
     * 单价
     */
    private BigDecimal unitPrice;
    /**
     * 拿货数量
     */
    private Integer quantity;
    /**
     * 总价(拿货门槛)
     */
    private BigDecimal totalPrice;
    /**
     * 保证金
     */
    private BigDecimal bail;
    /**
     * 证书等级背景图
     */
    private String backImg;
    /**
     * 是否展示在前台0否1是
     */
    private Integer isShow;
    private String remark;
    /**
     * 是否支持升级，0否1是
     */
    private Integer isUpgrade;
    /**
     * 推荐人奖励单价
     */
    private BigDecimal rewardUnitPrice;
    /**
     * 招募小白上限数量
     */
    private Integer freemanUpperNum;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getSkuId() {
        return skuId;
    }
    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }
    public Integer getAgentLevelId() {
        return agentLevelId;
    }
    public void setAgentLevelId(Integer agentLevelId) {
        this.agentLevelId = agentLevelId;
    }
    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
    public BigDecimal getBail() {
        return bail;
    }
    public void setBail(BigDecimal bail) {
        this.bail = bail;
    }
    public String getBackImg() {
        return backImg;
    }
    public void setBackImg(String backImg) {
        this.backImg = backImg == null ? null : backImg.trim();
    }
    public Integer getIsShow() {
        return isShow;
    }
    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
    public Integer getIsUpgrade() {
        return isUpgrade;
    }
    public void setIsUpgrade(Integer isUpgrade) {
        this.isUpgrade = isUpgrade;
    }
    public BigDecimal getRewardUnitPrice() {
        return rewardUnitPrice;
    }
    public void setRewardUnitPrice(BigDecimal rewardUnitPrice) {
        this.rewardUnitPrice = rewardUnitPrice;
    }
    public Integer getFreemanUpperNum() {
        return freemanUpperNum;
    }
    public void setFreemanUpperNum(Integer freemanUpperNum) {
        this.freemanUpperNum = freemanUpperNum;
    }
    @Override
    public String toString() {
        return "PfSkuAgent{" +
                "id=" + id +
                ", skuId=" + skuId +
                ", agentLevelId=" + agentLevelId +
                ", icon='" + icon + '\'' +
                ", unitPrice=" + unitPrice +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                ", bail=" + bail +
                ", backImg='" + backImg + '\'' +
                ", isShow=" + isShow +
                ", remark='" + remark + '\'' +
                ", isUpgrade=" + isUpgrade +
                ", rewardUnitPrice=" + rewardUnitPrice +
                '}';
    }
}