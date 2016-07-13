/*
 * PfUserRebate.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-06-14 Created
 */
package com.masiis.shop.dao.po;

import java.util.Date;

public class PfUserRebate {

    private Long id;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 升级通知单id
     */
    private Long userUpgradeNoticeId;
    /**
     * 获得奖励用户id
     */
    private Long userId;
    /**
     * 支付奖励用户id
     */
    private Long userPid;
    /**
     * 合伙升级订单id
     */
    private Long pfBorderId;
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
    public Long getUserUpgradeNoticeId() {
        return userUpgradeNoticeId;
    }
    public void setUserUpgradeNoticeId(Long userUpgradeNoticeId) {
        this.userUpgradeNoticeId = userUpgradeNoticeId;
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
}