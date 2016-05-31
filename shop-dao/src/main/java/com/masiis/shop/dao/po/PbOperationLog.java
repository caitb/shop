/*
 * PbOperationLog.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-05-26 Created
 */
package com.masiis.shop.dao.po;

import java.util.Date;

public class PbOperationLog {

    private Long id;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 操作用户id
     */
    private Long pbUserId;
    /**
     * 操作用户姓名
     */
    private String pbUserName;
    /**
     * 操作内容
     */
    private String operateContent;
    /**
     * 操作类型0新增1修改2删除
     */
    private Integer operateType;
    /**
     * 操作电脑ip
     */
    private String operateIp;
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
    public Long getPbUserId() {
        return pbUserId;
    }
    public void setPbUserId(Long pbUserId) {
        this.pbUserId = pbUserId;
    }
    public String getPbUserName() {
        return pbUserName;
    }
    public void setPbUserName(String pbUserName) {
        this.pbUserName = pbUserName == null ? null : pbUserName.trim();
    }
    public String getOperateContent() {
        return operateContent;
    }
    public void setOperateContent(String operateContent) {
        this.operateContent = operateContent == null ? null : operateContent.trim();
    }
    public Integer getOperateType() {
        return operateType;
    }
    public void setOperateType(Integer operateType) {
        this.operateType = operateType;
    }
    public String getOperateIp() {
        return operateIp;
    }
    public void setOperateIp(String operateIp) {
        this.operateIp = operateIp == null ? null : operateIp.trim();
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    @Override
    public String toString() {
        return "PbOperationLog{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", pbUserId=" + pbUserId +
                ", pbUserName='" + pbUserName + '\'' +
                ", operateContent='" + operateContent + '\'' +
                ", operateType=" + operateType +
                ", operateIp='" + operateIp + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}