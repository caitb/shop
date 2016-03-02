/*
 * ComSpuKey.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao.po;

import java.util.Date;

public class ComSpuKey {

    private Integer id;
    /**
     * spu名称
     */
    private String name;
    /**
     * 创建日期
     */
    private Date createTime;
    /**
     * 创建人
     */
    private Long createMan;
    /**
     * 商品分类id
     */
    private Integer categoryId;
    /**
     * 商品分类名称
     */
    private String categoryName;
    /**
     * 商品状态(0未审核1已审核)
     */
    private Integer status;
    /**
     * 上下架标志(0下架1上架)
     */
    private Integer isSale;
    /**
     * 删除标志(0否1是)
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