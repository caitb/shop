/*
 * PfSkuStatistic.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-16 Created
 */
package com.masiis.shop.dao.po;

public class PfSkuStatistic {

    private Integer id;
    /**
     * sku主键id
     */
    private Integer skuId;
    /**
     * 代理人数
     */
    private Integer agentNum;
    /**
     * 试用人数
     */
    private Integer trialNum;

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
    public Integer getAgentNum() {
        return agentNum;
    }
    public void setAgentNum(Integer agentNum) {
        this.agentNum = agentNum;
    }
    public Integer getTrialNum() {
        return trialNum;
    }
    public void setTrialNum(Integer trialNum) {
        this.trialNum = trialNum;
    }
}