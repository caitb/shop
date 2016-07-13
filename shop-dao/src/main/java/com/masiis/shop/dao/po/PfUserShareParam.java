/*
 * PfUserShareParam.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-06-14 Created
 */
package com.masiis.shop.dao.po;

import java.util.Date;

public class PfUserShareParam {

    private Long id;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 分享人
     */
    private Long fUserId;
    /**
     * skuID
     */
    private Integer skuId;
    /**
     * 可见推荐等级
     */
    private String agentLevelIds;
    private String remark;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Long getfUserId() {
        return fUserId;
    }
    public void setfUserId(Long fUserId) {
        this.fUserId = fUserId;
    }
    public Integer getSkuId() {
        return skuId;
    }
    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }
    public String getAgentLevelIds() {
        return agentLevelIds;
    }
    public void setAgentLevelIds(String agentLevelIds) {
        this.agentLevelIds = agentLevelIds == null ? null : agentLevelIds.trim();
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}