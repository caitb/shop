/*
 * PfMessageContent.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-07-12 Created
 */
package com.masiis.shop.dao.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.masiis.shop.common.util.EmojiUtils;

import java.util.Date;

public class PfMessageContent {

    /**
     * 主键id
     */
    private Long id;
    /**
     * 创建时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 创建人id,0为平台
     */
    private Long userId;
    /**
     * 消息类型: 1,个人发个人; 2,下级群发; 3,团队群发
     */
    private Integer type;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 消息内容url
     */
    private String contentUrl;
    /**
     * app消息跳转类型: 1,跳商品详情页
     */
    private Integer urlAppType;
    /**
     * app消息跳转参数,json格式
     */
    private String urlAppParam;
    /**
     * 状态: 0,草稿; 1,启用; 2,不启用; 3,删除
     */
    private Integer status;
    /**
     * 更新时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
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
    public Integer getType() {
        return type;
    }
    public void setType(Integer type) {
        this.type = type;
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
        this.contentUrl = contentUrl == null ? null : EmojiUtils.parseEmojiToUnicode(contentUrl.trim());
    }

    public Integer getUrlAppType() {
        return urlAppType;
    }

    public void setUrlAppType(Integer urlAppType) {
        this.urlAppType = urlAppType;
    }

    public String getUrlAppParam() {
        return urlAppParam;
    }

    public void setUrlAppParam(String urlAppParam) {
        this.urlAppParam = urlAppParam;
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
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}