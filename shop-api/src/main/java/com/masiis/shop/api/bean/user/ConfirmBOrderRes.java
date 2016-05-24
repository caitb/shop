package com.masiis.shop.api.bean.user;

import com.masiis.shop.api.bean.base.BaseBusinessRes;

/**
 * @Date 2016/5/24
 * @Auther lzh
 */
public class ConfirmBOrderRes extends BaseBusinessRes {
    private Integer sendType;
    private Integer isQueuing;
    private Integer queueNum;
    private String skuName;
    private Integer skuId;
    private String skuImg;
    private Integer agentLevelId;
    private String agentLevelName;
    private Integer quantity;
    private String productTotalFee;
    private String totalFee;
    private String bailFee;
    private String lowProfit;
    private String highProfit;

    public Integer getSendType() {
        return sendType;
    }

    public void setSendType(Integer sendType) {
        this.sendType = sendType;
    }

    public Integer getIsQueuing() {
        return isQueuing;
    }

    public void setIsQueuing(Integer isQueuing) {
        this.isQueuing = isQueuing;
    }

    public Integer getQueueNum() {
        return queueNum;
    }

    public void setQueueNum(Integer queueNum) {
        this.queueNum = queueNum;
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

    public String getSkuImg() {
        return skuImg;
    }

    public void setSkuImg(String skuImg) {
        this.skuImg = skuImg;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getProductTotalFee() {
        return productTotalFee;
    }

    public void setProductTotalFee(String productTotalFee) {
        this.productTotalFee = productTotalFee;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getBailFee() {
        return bailFee;
    }

    public void setBailFee(String bailFee) {
        this.bailFee = bailFee;
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
}
