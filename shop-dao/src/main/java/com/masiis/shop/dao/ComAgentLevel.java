/*
 * ComAgentLevel.java
 * Copyright(C) 2014-2016 ��ʿ����
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao;

import java.math.BigDecimal;
import java.util.Date;

/**
 * ����ȼ���
 * 
 * @author masiis
 * @version 1.0 2016-03-02
 */
public class ComAgentLevel {

    /**
     * ����ȼ�
     */
    private Integer id;
    /**
     * ����ʱ��
     */
    private Date createTime;
    /**
     * ����ȼ�����
     */
    private String name;
    /**
     * ͼƬ��ַ
     */
    private String imgUrl;
    /**
     * �û��ż�
     */
    private BigDecimal lower;
    /**
     * �ۿ�
     */
    private BigDecimal discount;
    private String remark;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
    public String getImgUrl() {
        return imgUrl;
    }
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }
    public BigDecimal getLower() {
        return lower;
    }
    public void setLower(BigDecimal lower) {
        this.lower = lower;
    }
    public BigDecimal getDiscount() {
        return discount;
    }
    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}