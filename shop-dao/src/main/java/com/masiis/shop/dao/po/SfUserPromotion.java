/*
 * SfUserPromotion.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-07-04 Created
 */
package com.masiis.shop.dao.po;

import org.apache.commons.lang.time.DateUtils;
import java.text.ParseException;
import java.util.Date;

public class SfUserPromotion {

    /**
     * 商城用户活动主键id
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
     * 活动编码
     */
    private String code;
    /**
     * 活动名称
     */
    private String name;
    /**
     * 活动介绍
     */
    private String introduction;
    /**
     * 活动类型(0:满赠)
     */
    private Integer type;
    /**
     * 活动对象(0粉丝1代言人)
     */
    private Integer personType;
    /**
     * 活动状态(0:进行中1:暂停2:结束)
     */
    private Integer status;
    /**
     * 活动开始时间
     */
    private Date beginTime;
    /**
     * 活动结束时间
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

    public SfUserPromotion() {
    }

    public SfUserPromotion(Integer id, String name, String remark, String introduction, Integer personType, String beginTimeStr, String endTimeStr) {
        this.id = id;
        this.name = name;
        this.remark = remark;
        this.personType = personType;
        this.setIntroduction(introduction);

        try {
            Date beginTime = DateUtils.parseDate(beginTimeStr, new String[]{"yyyy-MM-dd HH:mm:ss"});
            this.setBeginTime(beginTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            Date endTime   = DateUtils.parseDate(endTimeStr,   new String[]{"yyyy-MM-dd HH:mm:ss"});
            this.setEndTime(endTime);
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
    public String getIntroduction() {
        return introduction;
    }
    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }
    public Integer getType() {
        return type;
    }
    public void setType(Integer type) {
        this.type = type;
    }
    public Integer getPersonType() {
        return personType;
    }
    public void setPersonType(Integer personType) {
        this.personType = personType;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
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