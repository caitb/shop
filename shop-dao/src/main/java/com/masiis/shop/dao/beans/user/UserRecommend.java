package com.masiis.shop.dao.beans.user;

/**
 * @author muchaofeng
 * @date $date$ $time$
 */

public class UserRecommend {
    private Long userId;
    private Integer skuId;
    private Integer agentId;
    private Integer number;
    private String name;
    private String wxHeadImg;
    private String skuName;
    private String agentName;

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getNumber() {
        return number;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAgentName() {
        return agentName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWxHeadImg() {
        return wxHeadImg;
    }

    public void setWxHeadImg(String wxHeadImg) {
        this.wxHeadImg = wxHeadImg;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }
}
