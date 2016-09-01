/*
 * PfSysMenu.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-08-10 Created
 */
package com.masiis.shop.dao.po;

import java.util.Date;

public class PfSysMenu {

    private Integer id;
    /**
     * 创建时间
     */
    private Date createTime;
    private Long createMan;
    /**
     * 类型(1品牌2家族2团队)
     */
    private Integer type;
    /**
     * 值
     */
    private Integer value;
    /**
     * 排序小到大
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
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

    @Override
    public String toString() {
        return "PfSysMenu{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", createMan=" + createMan +
                ", type=" + type +
                ", value=" + value +
                ", sort=" + sort +
                ", remark='" + remark + '\'' +
                '}';
    }
}