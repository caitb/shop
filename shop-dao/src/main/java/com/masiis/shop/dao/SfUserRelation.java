/*
 * SfUserRelation.java
 * Copyright(C) 2014-2016 ��ʿ����
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao;

import java.util.Date;

/**
 * �����û���ϵ��
 * 
 * @author masiis
 * @version 1.0 2016-03-02
 */
public class SfUserRelation {

    private Long id;
    /**
     * ����ʱ��
     */
    private Date createTime;
    /**
     * �û�id
     */
    private Long userId;
    /**
     * ��һ���û�id
     */
    private Long parentUserId;
    /**
     * ��ע
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
    public Long getParentUserId() {
        return parentUserId;
    }
    public void setParentUserId(Long parentUserId) {
        this.parentUserId = parentUserId;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}