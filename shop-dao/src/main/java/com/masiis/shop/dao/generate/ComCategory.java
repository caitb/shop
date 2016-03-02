/*
 * ComCategory.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao.generate;

import java.util.Date;

/**
 * 商品分类表
 * 
 * @author masiis
 * @version 1.0 2016-03-02
 */
public class ComCategory {

    /**
     * 主键id
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
     * 修改时间
     */
    private Date modifyTime;
    /**
     * 修改人
     */
    private Long modifyMan;
    /**
     * 名称
     */
    private String name;
    /**
     * 等级
     */
    private Byte level;
    /**
     * 父id
     */
    private Integer pid;
    /**
     * 展示顺序(升序)
     */
    private Integer sort;
    /**
     * 是否启用：0 否 1 是
     */
    private Integer isValidate;
    /**
     * 备注
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