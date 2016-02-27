/*
 * SysMenu.java
 * Copyright(C) 20xx-2015 xxxxxx��˾
 * All rights reserved.
 * -----------------------------------------------
 * 2016-02-24 Created
 */
package com.masiis.shop.dao.menu;

import java.util.Date;

/**
 * 
 * 
 * @author ���ܴ���
 * @version 1.0 2016-02-24
 */
public class SysMenu {

    /**
     * ����
     */
    private Long id;
    /**
     * �˵�����
     */
    private String name;
    /**
     * ͼ��
     */
    private String icon;
    /**
     * ϵͳurl
     */
    private String url;
    /**
     * ��id
     */
    private Long parentId;
    /**
     * �Ƿ�ɾ��(0δɾ��1��ɾ��)
     */
    private Integer isDeleted;
    /**
     * ����ʱ��
     */
    private Date createTime;
    /**
     * �޸�ʱ��
     */
    private Date updateTime;
    /**
     * ����
     */
    private Integer rank;
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
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }
    public Long getParentId() {
        return parentId;
    }
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
    public Integer getIsDeleted() {
        return isDeleted;
    }
    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    public Integer getRank() {
        return rank;
    }
    public void setRank(Integer rank) {
        this.rank = rank;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}