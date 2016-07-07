/*
 * ComSkuMaterialGroup.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-07-04 Created
 */
package com.masiis.shop.dao.po;

import java.util.Date;

public class ComSkuMaterialGroup {

    /**
     * 素材组id
     */
    private Integer id;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建人
     */
    private Long createMan;
    /**
     * 素材库id
     */
    private Integer materialLibraryId;
    /**
     * 素材组名称
     */
    private String name;
    /**
     * 是否展示在B端(0否1是)
     */
    private Integer isBShow;
    /**
     * 是否展示在C端(0否1是)
     */
    private Integer isCShow;
    /**
     * 排序序号，大到小
     */
    private Integer sort;
    /**
     * 备注
     */
    private String remark;
    /**
     * 图标
     */
    private String icon;

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

    public Integer getMaterialLibraryId() {
        return materialLibraryId;
    }

    public void setMaterialLibraryId(Integer materialLibraryId) {
        this.materialLibraryId = materialLibraryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIsBShow() {
        return isBShow;
    }

    public void setIsBShow(Integer isBShow) {
        this.isBShow = isBShow;
    }

    public Integer getIsCShow() {
        return isCShow;
    }

    public void setIsCShow(Integer isCShow) {
        this.isCShow = isCShow;
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
        this.remark = remark;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "ComSkuMaterialGroup{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", createMan=" + createMan +
                ", materialLibraryId=" + materialLibraryId +
                ", name='" + name + '\'' +
                ", isBShow=" + isBShow +
                ", isCShow=" + isCShow +
                ", sort=" + sort +
                ", remark='" + remark + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }
}