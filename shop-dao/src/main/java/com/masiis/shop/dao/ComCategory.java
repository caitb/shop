/*
 * ComCategory.java
 * Copyright(C) 2014-2016 ��ʿ����
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao;

import java.util.Date;

/**
 * ��Ʒ�����
 * 
 * @author masiis
 * @version 1.0 2016-03-02
 */
public class ComCategory {

    /**
     * ����id
     */
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
     * �޸�ʱ��
     */
    private Date modifyTime;
    /**
     * �޸���
     */
    private Long modifyMan;
    /**
     * ����
     */
    private String name;
    /**
     * �ȼ�
     */
    private Byte level;
    /**
     * ��id
     */
    private Integer pid;
    /**
     * չʾ˳��(����)
     */
    private Integer sort;
    /**
     * �Ƿ����ã�0 �� 1 ��
     */
    private Integer isValidate;
    /**
     * ��ע
     */
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
    public Date getModifyTime() {
        return modifyTime;
    }
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
    public Long getModifyMan() {
        return modifyMan;
    }
    public void setModifyMan(Long modifyMan) {
        this.modifyMan = modifyMan;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
    public Byte getLevel() {
        return level;
    }
    public void setLevel(Byte level) {
        this.level = level;
    }
    public Integer getPid() {
        return pid;
    }
    public void setPid(Integer pid) {
        this.pid = pid;
    }
    public Integer getSort() {
        return sort;
    }
    public void setSort(Integer sort) {
        this.sort = sort;
    }
    public Integer getIsValidate() {
        return isValidate;
    }
    public void setIsValidate(Integer isValidate) {
        this.isValidate = isValidate;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}