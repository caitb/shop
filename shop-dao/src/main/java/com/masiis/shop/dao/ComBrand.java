/*
 * ComBrand.java
 * Copyright(C) 2014-2016 ��ʿ����
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao;

import java.util.Date;

/**
 * Ʒ�Ʊ�
 * 
 * @author masiis
 * @version 1.0 2016-03-02
 */
public class ComBrand {

    private Integer id;
    private Date createTime;
    /**
     * Ʒ��������
     */
    private String cname;
    /**
     * Ʒ��Ӣ����
     */
    private String ename;
    /**
     * logo���
     */
    private String logoUrl;
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
    public String getCname() {
        return cname;
    }
    public void setCname(String cname) {
        this.cname = cname == null ? null : cname.trim();
    }
    public String getEname() {
        return ename;
    }
    public void setEname(String ename) {
        this.ename = ename == null ? null : ename.trim();
    }
    public String getLogoUrl() {
        return logoUrl;
    }
    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl == null ? null : logoUrl.trim();
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}