package com.masiis.shop.dao.beans.order;

import com.masiis.shop.dao.po.ComUserAddress;
import com.mysql.jdbc.PacketTooBigException;

import java.math.BigDecimal;

/**
 * BOrderConfirm
 *
 * @author ZhaoLiang
 * @date 2016/4/15
 */
public class BOrderConfirm {
    /**
     * 订单类型
     */
    private Integer orderType;
    /**
     * 拿货方式
     */
    private Integer sendType;
    /**
     * sku图片
     */
    private String skuImg;
    /**
     * sku名称
     */
    private String skuName;
    /**
     * sku数量
     */
    private Integer skuQuantity;
    /**
     * 代理等级ID
     */
    private Integer agentLevelId;
    /**
     * 代理等级名称
     */
    private String agentLevelName;
    /**
     * 商品总金额
     */
    private BigDecimal productTotalPrice;
    /**
     * 保证金
     */
    private BigDecimal bailAmount;
    /**
     * 最低利润
     */
    private BigDecimal lowProfit;
    /**
     * 最高利润
     */
    private BigDecimal highProfit;
    /**
     * 订单总金额
     */
    private BigDecimal orderTotalPrice;
    /**
     * 用户地址
     */
    private ComUserAddress comUserAddress;

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Integer getSendType() {
        return sendType;
    }

    public void setSendType(Integer sendType) {
        this.sendType = sendType;
    }

    public String getSkuImg() {
        return skuImg;
    }

    public void setSkuImg(String skuImg) {
        this.skuImg = skuImg;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public Integer getSkuQuantity() {
        return skuQuantity;
    }

    public void setSkuQuantity(Integer skuQuantity) {
        this.skuQuantity = skuQuantity;
    }

    public Integer getAgentLevelId() {
        return agentLevelId;
    }

    public void setAgentLevelId(Integer agentLevelId) {
        this.agentLevelId = agentLevelId;
    }

    public String getAgentLevelName() {
        return agentLevelName;
    }

    public void setAgentLevelName(String agentLevelName) {
        this.agentLevelName = agentLevelName;
    }

    public BigDecimal getProductTotalPrice() {
        return productTotalPrice;
    }

    public void setProductTotalPrice(BigDecimal productTotalPrice) {
        this.productTotalPrice = productTotalPrice;
    }

    public BigDecimal getBailAmount() {
        return bailAmount;
    }

    public void setBailAmount(BigDecimal bailAmount) {
        this.bailAmount = bailAmount;
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

    public BigDecimal getOrderTotalPrice() {
        return orderTotalPrice;
    }

    public void setOrderTotalPrice(BigDecimal orderTotalPrice) {
        this.orderTotalPrice = orderTotalPrice;
    }

    public ComUserAddress getComUserAddress() {
        return comUserAddress;
    }

    public void setComUserAddress(ComUserAddress comUserAddress) {
        this.comUserAddress = comUserAddress;
    }
}
