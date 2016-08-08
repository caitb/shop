/*
 * SfTurnTableRule.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-07-29 Created
 */
package com.masiis.shop.dao.po;

import java.util.Date;

public class SfTurnTableRule {

    /**
     * 转盘规则id
     */
    private Integer id;
    /**
     * 名称
     */
    private String name;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建人
     */
    private Long createMan;
    /**
     * 转盘id
     */
    private Integer turnTableId;
    /**
     * 抽奖的次数
     */
    private Integer times;
    /**
     * 状态(0:失效1:生效)
     */
    private Integer status;
    /**
     * 转盘对象(0:B端1:C端)
     */
    private Integer type;
    /**
     * 修改时间
     */
    private Date modifyTime;
    /**
     * 修改人
     */
    private Long modifyMan;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    public Integer getTurnTableId() {
        return turnTableId;
    }
    public void setTurnTableId(Integer turnTableId) {
        this.turnTableId = turnTableId;
    }
    public Integer getTimes() {
        return times;
    }
    public void setTimes(Integer times) {
        this.times = times;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public Integer getType() {
        return type;
    }
    public void setType(Integer type) {
        this.type = type;
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
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}