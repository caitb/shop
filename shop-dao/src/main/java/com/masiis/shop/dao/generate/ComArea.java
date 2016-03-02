/*
 * ComArea.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao.generate;

import java.util.Date;

/**
 * 地址表
 * 
 * @author masiis
 * @version 1.0 2016-03-02
 */
public class ComArea {

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
     * 编码
     */
    private String code;
    /**
     * 名称
     */
    private String name;
    /**
     * 父id
     */
    private Integer pid;
    /**
     * 树等级
     */
    private Integer level;
    /**
     * 拼音全称
     */
    private String nameEn;
    /**
     * 拼音简称
     */
    private String shortnameEn;
    /**
     * 排序，小到大
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