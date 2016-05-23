package com.masiis.shop.api.bean.user;

import com.masiis.shop.api.bean.base.BaseBusinessRes;

import java.util.List;

/**
 * @Date 2016/5/23
 * @Auther lzh
 */
public class PartnerApplyRegisterRes extends BaseBusinessRes {
    /**
     * skuName
     */
    private String skuName;
    /**
     * 上级代理等级id
     */
    private Integer pUserLevelId;
    /**
     * 上级代理微信昵称
     */
    private String pWxNkName;
    /**
     * 发货方式
     */
    private Integer sendType;
    /**
     * 是否排单
     */
    private Integer isQueuing;
    /**
     * 排单人数
     */
    private Integer queueNum;
    /**
     * 可代理的等级list
     */
    private List<AgentSkuView> agentSkuViews;

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public Integer getpUserLevelId() {
        return pUserLevelId;
    }

    public void setpUserLevelId(Integer pUserLevelId) {
        this.pUserLevelId = pUserLevelId;
    }

    public String getpWxNkName() {
        return pWxNkName;
    }

    public void setpWxNkName(String pWxNkName) {
        this.pWxNkName = pWxNkName;
    }

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

    public List<AgentSkuView> getAgentSkuViews() {
        return agentSkuViews;
    }

    public void setAgentSkuViews(List<AgentSkuView> agentSkuViews) {
        this.agentSkuViews = agentSkuViews;
    }
}
