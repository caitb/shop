/*
 * PfMessageSrRelation.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-07-12 Created
 */
package com.masiis.shop.dao.po;

import java.util.Date;

public class PfMessageSrRelation {

    /**
     * 主键id
     */
    private Long id;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 消息内容id
     */
    private Long pfMessageContentId;
    /**
     *  发送方用户id
     */
    private Long fromUser;
    /**
     * 接收方用户id
     */
    private Long toUser;
    /**
     * 状态: 1,启用; 2,不启用; 3,删除
     */
    private Integer status;
    /**
     * 消息类型: 1,个人发个人; 2,下级群发; 3,团队群发
     */
    private Integer mType;
    /**
     * 是否查看: 0,未查看; 1,已查看
     */
    private Integer isSee;
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
    public Long getPfMessageContentId() {
        return pfMessageContentId;
    }
    public void setPfMessageContentId(Long pfMessageContentId) {
        this.pfMessageContentId = pfMessageContentId;
    }
    public Long getFromUser() {
        return fromUser;
    }
    public void setFromUser(Long fromUser) {
        this.fromUser = fromUser;
    }
    public Long getToUser() {
        return toUser;
    }
    public void setToUser(Long toUser) {
        this.toUser = toUser;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getmType() {
        return mType;
    }

    public void setmType(Integer mType) {
        this.mType = mType;
    }

    public Integer getIsSee() {
        return isSee;
    }
    public void setIsSee(Integer isSee) {
        this.isSee = isSee;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}