/*
 * PfBorderOperationLog.java
 * Copyright(C) 2014-2016 ��ʿ����
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao;

import java.util.Date;

/**
 * ƽ̨������������־��
 * 
 * @author masiis
 * @version 1.0 2016-03-02
 */
public class PfBorderOperationLog {

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
    private Long pfBorderId;
    /**
     * ����״̬
     */
    private Integer pfBorderStatus;
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
    public Long getPfBorderId() {
        return pfBorderId;
    }
    public void setPfBorderId(Long pfBorderId) {
        this.pfBorderId = pfBorderId;
    }
    public Integer getPfBorderStatus() {
        return pfBorderStatus;
    }
    public void setPfBorderStatus(Integer pfBorderStatus) {
        this.pfBorderStatus = pfBorderStatus;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}