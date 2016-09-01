package com.masiis.shop.dao.beans.family;

/**
 * 我的团队列表po
 * Created by wangbingjian on 2016/8/23.
 */
public class TeamListPo {

    private Long userId;

    private String userName;
    /**
     * 微信号
     */
    private String wxId;
    /**
     * 授权书编码
     */
    private String code;

    private Integer agentLevel;

    private String agentLevelName;

    private String agentLevelImg;

    private String wxHeadImg;

    private Integer skuId;

    private Integer userSkuId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getWxId() {
        return wxId;
    }

    public void setWxId(String wxId) {
        this.wxId = wxId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getAgentLevel() {
        return agentLevel;
    }

    public void setAgentLevel(Integer agentLevel) {
        this.agentLevel = agentLevel;
    }

    public String getAgentLevelImg() {
        return agentLevelImg;
    }

    public void setAgentLevelImg(String agentLevelImg) {
        this.agentLevelImg = agentLevelImg;
    }

    public String getWxHeadImg() {
        return wxHeadImg;
    }

    public void setWxHeadImg(String wxHeadImg) {
        this.wxHeadImg = wxHeadImg;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public Integer getUserSkuId() {
        return userSkuId;
    }

    public void setUserSkuId(Integer userSkuId) {
        this.userSkuId = userSkuId;
    }

    public String getAgentLevelName() {
        return agentLevelName;
    }

    public void setAgentLevelName(String agentLevelName) {
        this.agentLevelName = agentLevelName;
    }
}