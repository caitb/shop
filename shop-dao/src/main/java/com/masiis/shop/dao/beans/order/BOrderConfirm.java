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
     * sku主键ID
     */
    private Integer skuId;
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
     * 微信Id
     */
    private String weiXinId;
    /**
     * 代理等级ID
     */
    private Integer agentLevelId;
    /**
     * 商品总金额
     */
    private String productTotalPrice;
    /**
     * 保证金
     */
    private String bailAmount;
    /**
     * 最低利润
     */
    private String lowProfit;
    /**
     * 最高利润
     */
    private String highProfit;
    /**
     * 订单总金额
     */
    private String orderTotalPrice;
    /**
     * 用户地址id
     */
    private Long userAddressId;
    /**
     * 用户地址
     */
    private ComUserAddress comUserAddress;
    /**
     * 用户留言
     */
    private String userMessage;

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

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
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

    public String getWeiXinId() {
        return weiXinId;
    }

    public void setWeiXinId(String weiXinId) {
        this.weiXinId = weiXinId;
    }

    public Integer getAgentLevelId() {
        return agentLevelId;
    }

    public void setAgentLevelId(Integer agentLevelId) {
        this.agentLevelId = agentLevelId;
    }

    public String getProductTotalPrice() {
        return productTotalPrice;
    }

    public void setProductTotalPrice(String productTotalPrice) {
        this.productTotalPrice = productTotalPrice;
    }

    public String getBailAmount() {
        return bailAmount;
    }

    public void setBailAmount(String bailAmount) {
        this.bailAmount = bailAmount;
    }

    public String getLowProfit() {
        return lowProfit;
    }

    public void setLowProfit(String lowProfit) {
        this.lowProfit = lowProfit;
    }

    public String getHighProfit() {
        return highProfit;
    }

    public void setHighProfit(String highProfit) {
        this.highProfit = highProfit;
    }

    public String getOrderTotalPrice() {
        return orderTotalPrice;
    }

    public void setOrderTotalPrice(String orderTotalPrice) {
        this.orderTotalPrice = orderTotalPrice;
    }

    public Long getUserAddressId() {
        return userAddressId;
    }

    public void setUserAddressId(Long userAddressId) {
        this.userAddressId = userAddressId;
    }

    public ComUserAddress getComUserAddress() {
        return comUserAddress;
    }

    public void setComUserAddress(ComUserAddress comUserAddress) {
        this.comUserAddress = comUserAddress;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    @Override
    public String toString() {
        return "BOrderConfirm{" +
                "orderType=" + orderType +
                ", sendType=" + sendType +
                ", skuId=" + skuId +
                ", skuImg='" + skuImg + '\'' +
                ", skuName='" + skuName + '\'' +
                ", skuQuantity=" + skuQuantity +
                ", weiXinId='" + weiXinId + '\'' +
                ", agentLevelId=" + agentLevelId +
                ", productTotalPrice='" + productTotalPrice + '\'' +
                ", bailAmount='" + bailAmount + '\'' +
                ", lowProfit='" + lowProfit + '\'' +
                ", highProfit='" + highProfit + '\'' +
                ", orderTotalPrice='" + orderTotalPrice + '\'' +
                ", userAddressId=" + userAddressId +
                ", comUserAddress=" + comUserAddress +
                ", userMessage='" + userMessage + '\'' +
                '}';
    }
}
