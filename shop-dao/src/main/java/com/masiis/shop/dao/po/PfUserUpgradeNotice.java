/*
 * PfUserUpgradeNotice.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-06-14 Created
 */
package com.masiis.shop.dao.po;

import java.util.Date;

public class PfUserUpgradeNotice {

    private Long id;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 单据编码
     */
    private String code;
    /**
     * 申请人id
     */
    private Long userId;
    /**
     * 上级合伙人id
     */
    private Long userPid;
    /**
     * 合伙skuId
     */
    private Integer skuId;
    /**
     * 原合伙等级
     */
    private Integer orgAgentLevelId;
    /**
     * 意愿合伙等级
     */
    private Integer wishAgentLevelId;
    /**
     * 状态，0未处理1处理中2待支付3完成
     */
    private Integer status;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 升级订单id
     */
    private Long pfBorderId;
    private String remark;
    /**
     * 上级处理状态，0未处理1暂不升级2我要升级
     */
    private Integer upStatus;

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
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Long getUserPid() {
        return userPid;
    }
    public void setUserPid(Long userPid) {
        this.userPid = userPid;
    }
    public Integer getSkuId() {
        return skuId;
    }
    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }
    public Integer getOrgAgentLevelId() {
        return orgAgentLevelId;
    }
    public void setOrgAgentLevelId(Integer orgAgentLevelId) {
        this.orgAgentLevelId = orgAgentLevelId;
    }
    public Integer getWishAgentLevelId() {
        return wishAgentLevelId;
    }
    public void setWishAgentLevelId(Integer wishAgentLevelId) {
        this.wishAgentLevelId = wishAgentLevelId;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getPfBorderId() {
        return pfBorderId;
    }
    public void setPfBorderId(Long pfBorderId) {
        this.pfBorderId = pfBorderId;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
    public Integer getUpStatus() {
        return upStatus;
    }
    public void setUpStatus(Integer upStatus) {
        this.upStatus = upStatus;
    }
}