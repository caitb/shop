/*
 * SfTurnTable.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-07-29 Created
 */
package com.masiis.shop.dao.po;

import com.masiis.shop.common.util.TurnTableMakeUtils;
import org.apache.commons.lang.time.DateUtils;

import java.text.ParseException;
import java.util.Date;

public class SfTurnTable {



    /**
     * 转盘主键id
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
     * 转盘名称
     */
    private String name;
    /**
     * 转盘编码
     */
    private String code;
    /**
     * 转盘的状态(0:进行中1:暂停2:结束)
     */
    private Integer status;
    /**
     * 转盘描述
     */
    private String describe;
    /**
     * 转盘开始时间
     */
    private Date beginTime;
    /**
     * 转盘结束时间
     */
    private Date endTime;
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

    public SfTurnTable() {
    }

    public SfTurnTable(Integer id, String name, String describe, String remark, String beginTimeStr, String endTimeStr) {
        this.id = id;
        setName(name);
        setDescribe(describe);
        setRemark(remark);

        try {
            this.beginTime = DateUtils.parseDate(beginTimeStr, new String[]{"yyyy-MM-dd HH:mm:ss"});
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            this.endTime   = DateUtils.parseDate(endTimeStr,   new String[]{"yyyy-MM-dd HH:mm:ss"});
        } catch (ParseException e) {
            e.printStackTrace();
        }




    }

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
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public String getDescribe() {
        return describe;
    }
    public void setDescribe(String describe) {
        this.describe = describe == null ? null : describe.trim();
    }
    public Date getBeginTime() {
        return beginTime;
    }
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }
    public Date getEndTime() {
        return endTime;
    }
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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