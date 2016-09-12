/*
 * PfUserSearchLog.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-09-12 Created
 */
package com.masiis.shop.dao.po;

import java.util.Date;

public class PfUserSearchLog {

    private Long id;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 搜索内容
     */
    private String content;
    /**
     * 是否被删除（0未删除；1已删除）
     */
    private Integer deleted;

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
    public Integer getDeleted() {
        return deleted;
    }
    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
}