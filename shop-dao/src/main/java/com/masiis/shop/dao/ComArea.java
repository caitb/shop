/*
 * ComArea.java
 * Copyright(C) 2014-2016 ��ʿ����
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao;

import java.util.Date;

/**
 * ��ַ��
 * 
 * @author masiis
 * @version 1.0 2016-03-02
 */
public class ComArea {

    private Integer id;
    /**
     * ����ʱ��
     */
    private Date createTime;
    /**
     * ������
     */
    private Long createMan;
    /**
     * ����
     */
    private String code;
    /**
     * ����
     */
    private String name;
    /**
     * ��id
     */
    private Integer pid;
    /**
     * ���ȼ�
     */
    private Integer level;
    /**
     * ƴ��ȫ��
     */
    private String nameEn;
    /**
     * ƴ�����
     */
    private String shortnameEn;
    /**
     * ����С����
     */
    private Integer sort;
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
    public Long getCreateMan() {
        return createMan;
    }
    public void setCreateMan(Long createMan) {
        this.createMan = createMan;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
    public Integer getPid() {
        return pid;
    }
    public void setPid(Integer pid) {
        this.pid = pid;
    }
    public Integer getLevel() {
        return level;
    }
    public void setLevel(Integer level) {
        this.level = level;
    }
    public String getNameEn() {
        return nameEn;
    }
    public void setNameEn(String nameEn) {
        this.nameEn = nameEn == null ? null : nameEn.trim();
    }
    public String getShortnameEn() {
        return shortnameEn;
    }
    public void setShortnameEn(String shortnameEn) {
        this.shortnameEn = shortnameEn == null ? null : shortnameEn.trim();
    }
    public Integer getSort() {
        return sort;
    }
    public void setSort(Integer sort) {
        this.sort = sort;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}