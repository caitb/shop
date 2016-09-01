/*
 * PfUserOrganization.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-08-18 Created
 */
package com.masiis.shop.dao.po;

import java.util.Date;

public class PfUserOrganization {

    private Integer id;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date modifyTime;
    /**
     * 品牌id
     */
    private Integer brandId;
    /**
     * 合伙等级
     */
    private Integer agentLevelId;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 名称
     */
    private String name;
    /**
     * lgo
     */
    private String logo;
    /**
     * 背景图
     */
    private String backImg;
    /**
     * 微信二维码
     */
    private String wxQrCode;
    /**
     * 微信号
     */
    private String wxId;
    /**
     * 加好友描述
     */
    private String addDescription;
    /**
     * 一句话介绍
     */
    private String slogan;
    /**
     * 介绍
     */
    private String introduction;
    /**
     * 状态(0:无效1:有效)
     */
    private Integer status;
    /**
     * 已招募小白数量
     */
    private Integer freemanNum;
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
    public Date getModifyTime() {
        return modifyTime;
    }
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
    public Integer getBrandId() {
        return brandId;
    }
    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }
    public Integer getAgentLevelId() {
        return agentLevelId;
    }
    public void setAgentLevelId(Integer agentLevelId) {
        this.agentLevelId = agentLevelId;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
    public String getLogo() {
        return logo;
    }
    public void setLogo(String logo) {
        this.logo = logo == null ? null : logo.trim();
    }
    public String getBackImg() {
        return backImg;
    }
    public void setBackImg(String backImg) {
        this.backImg = backImg == null ? null : backImg.trim();
    }
    public String getWxQrCode() {
        return wxQrCode;
    }
    public void setWxQrCode(String wxQrCode) {
        this.wxQrCode = wxQrCode == null ? null : wxQrCode.trim();
    }
    public String getWxId() {
        return wxId;
    }
    public void setWxId(String wxId) {
        this.wxId = wxId == null ? null : wxId.trim();
    }
    public String getAddDescription() {
        return addDescription;
    }
    public void setAddDescription(String addDescription) {
        this.addDescription = addDescription == null ? null : addDescription.trim();
    }
    public String getSlogan() {
        return slogan;
    }
    public void setSlogan(String slogan) {
        this.slogan = slogan == null ? null : slogan.trim();
    }
    public String getIntroduction() {
        return introduction;
    }
    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public Integer getFreemanNum() {
        return freemanNum;
    }
    public void setFreemanNum(Integer freemanNum) {
        this.freemanNum = freemanNum;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}