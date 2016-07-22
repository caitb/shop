/*
 * PbStorageBillOperationLog.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-07-22 Created
 */
package com.masiis.shop.dao.po;

import java.util.Date;

public class PbStorageBillOperationLog {

    private Long id;
    private Date createTime;
    /**
     * 库存变更单id
     */
    private Integer pbStorageBillId;
    /**
     * 变更前单据状态
     */
    private Integer prevStatus;
    /**
     * 变更后单据状态
     */
    private Integer nextStatus;
    /**
     * 操作类型: 1,创建单据; 2,审核单据; 3,处理单据
     */
    private Integer handleType;
    /**
     * 操作人id
     */
    private Long handleMan;
    /**
     * 备注
     */
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
    public Integer getPbStorageBillId() {
        return pbStorageBillId;
    }
    public void setPbStorageBillId(Integer pbStorageBillId) {
        this.pbStorageBillId = pbStorageBillId;
    }
    public Integer getPrevStatus() {
        return prevStatus;
    }
    public void setPrevStatus(Integer prevStatus) {
        this.prevStatus = prevStatus;
    }
    public Integer getNextStatus() {
        return nextStatus;
    }
    public void setNextStatus(Integer nextStatus) {
        this.nextStatus = nextStatus;
    }
    public Integer getHandleType() {
        return handleType;
    }
    public void setHandleType(Integer handleType) {
        this.handleType = handleType;
    }
    public Long getHandleMan() {
        return handleMan;
    }
    public void setHandleMan(Long handleMan) {
        this.handleMan = handleMan;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}