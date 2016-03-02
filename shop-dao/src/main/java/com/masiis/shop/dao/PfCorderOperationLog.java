/*
 * PfCorderOperationLog.java
 * Copyright(C) 2014-2016 ��ʿ����
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao;

import java.util.Date;

/**
 * ƽ̨��������������־��
 * 
 * @author masiis
 * @version 1.0 2016-03-02
 */
public class PfCorderOperationLog {

    private Long id;
    /**
     * ����ʱ��
     */
    private Date createTime;
    /**
     * ������
     */
    private Long createMan;
    /**
     * ������
     */
    private Long pfCorderId;
    /**
     * ����״̬
     */
    private Integer pfCorderStatus;
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
    public Long getCreateMan() {
        return createMan;
    }
    public void setCreateMan(Long createMan) {
        this.createMan = createMan;
    }
    public Long getPfCorderId() {
        return pfCorderId;
    }
    public void setPfCorderId(Long pfCorderId) {
        this.pfCorderId = pfCorderId;
    }
    public Integer getPfCorderStatus() {
        return pfCorderStatus;
    }
    public void setPfCorderStatus(Integer pfCorderStatus) {
        this.pfCorderStatus = pfCorderStatus;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}