/*
 * ComSpuKey.java
 * Copyright(C) 2014-2016 ��ʿ����
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao;

import java.util.Date;

public class ComSpuKey {

    private Integer id;
    /**
     * spu����
     */
    private String name;
    /**
     * ��������
     */
    private Date createTime;
    /**
     * ������
     */
    private Long createMan;
    /**
     * ��Ʒ����id
     */
    private Integer categoryId;
    /**
     * ��Ʒ��������
     */
    private String categoryName;
    /**
     * ��Ʒ״̬(0δ���1�����)
     */
    private Integer status;
    /**
     * ���¼ܱ�־(0�¼�1�ϼ�)
     */
    private Integer isSale;
    /**
     * ɾ����־(0��1��)
     */
    private Integer isDelete;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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
    public Integer getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName == null ? null : categoryName.trim();
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public Integer getIsSale() {
        return isSale;
    }
    public void setIsSale(Integer isSale) {
        this.isSale = isSale;
    }
    public Integer getIsDelete() {
        return isDelete;
    }
    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
}