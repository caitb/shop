/*
 * SfSysMessage.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-08-06 Created
 */
package com.masiis.shop.dao.po;

import java.util.Date;

public class SfSysMessage {

    /**
     * 主键id
     */
    private Long id;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 接收消息用户id
     */
    private Long userId;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 浏览器跳转url
     */
    private String contentUrl;
    /**
     * app跳转类型
     */
    private Integer appUrlType;
    /**
     * app跳转参数，json格式
     */
    private String appUrlParam;
    /**
     * 消息状态: 1,启用; 2,不启用
     */
    private Integer status;
    /**
     * 消息类型: 1,操作通知; 
     */
    private Integer mType;
    /**
     * 是否查看: 0,未查看; 1,已查看
     */
    private Integer isSee;
    /**
     * 是否发送: 0,未发送; 1,已发送
     */
    private Integer isSend;
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
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
    public String getContentUrl() {
        return contentUrl;
    }
    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl == null ? null : contentUrl.trim();
    }
    public Integer getAppUrlType() {
        return appUrlType;
    }
    public void setAppUrlType(Integer appUrlType) {
        this.appUrlType = appUrlType;
    }
    public String getAppUrlParam() {
        return appUrlParam;
    }
    public void setAppUrlParam(String appUrlParam) {
        this.appUrlParam = appUrlParam == null ? null : appUrlParam.trim();
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

    public Integer getIsSend() {
        return isSend;
    }

    public void setIsSend(Integer isSend) {
        this.isSend = isSend;
    }

    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}