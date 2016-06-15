package com.masiis.shop.dao.beans.order;

import java.math.BigDecimal;

/**
 * 升级订单详情信息
 */
public class BOrderUpgradeDetail {

    private String skuName;                   //商品名称
    private String currentAgentLevelName;    //当前级别
    private String applyAgentLevelName;      //申请级别
    private String pAgentName;               //上级代理名
    private Integer quantity;                //代理数量
    private BigDecimal totalPrice;           //总价共支付

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getCurrentAgentLevelName() {
        return currentAgentLevelName;
    }

    public void setCurrentAgentLevelName(String currentAgentLevelName) {
        this.currentAgentLevelName = currentAgentLevelName;
    }

    public String getApplyAgentLevelName() {
        return applyAgentLevelName;
    }

    public void setApplyAgentLevelName(String applyAgentLevelName) {
        this.applyAgentLevelName = applyAgentLevelName;
    }

    public String getpAgentName() {
        return pAgentName;
    }

    public void setpAgentName(String pAgentName) {
        this.pAgentName = pAgentName;
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
}
