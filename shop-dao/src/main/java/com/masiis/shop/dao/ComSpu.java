/*
 * ComSpu.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao;

import java.util.Date;

/**
 * 商品spu表
 * 
 * @author masiis
 * @version 1.0 2016-03-02
 */
public class ComSpu extends ComSpuKey {

    /**
     * 品牌id
     */
    private Integer brandId;
    /**
     * 最后一次上架时间
     */
    private Date upTime;
    /**
     * 最后一次下架时间
     */
    private Date downTime;
    /**
     * 修改时间
     */
    private Date modifyTime;
    /**
     * 修改人
     */
    private Long modifyMan;
    /**
     * 广告语
     */
    private String slogan;
    /**
     * 可试用标志位(0否1是)
     */
    private Integer isTrial;
    /**
     * 商品描述
     */
    private String content;
    /**
     * 重量
     */
    private Long weight;
    /**
     * 长
     */
    private Long packLength;
    /**
     * 宽
     */
    private Long packWidth;
    /**
     * 高
     */
    private Long packHeight;
    /**
     * 备注
     */
    private String remark;

    public Integer getBrandId() {
        return brandId;
    }
    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }
    public Date getUpTime() {
        return upTime;
    }
    public void setUpTime(Date upTime) {
        this.upTime = upTime;
    }
    public Date getDownTime() {
        return downTime;
    }
    public void setDownTime(Date downTime) {
        this.downTime = downTime;
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
    public String getSlogan() {
        return slogan;
    }
    public void setSlogan(String slogan) {
        this.slogan = slogan == null ? null : slogan.trim();
    }
    public Integer getIsTrial() {
        return isTrial;
    }
    public void setIsTrial(Integer isTrial) {
        this.isTrial = isTrial;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
    public Long getWeight() {
        return weight;
    }
    public void setWeight(Long weight) {
        this.weight = weight;
    }
    public Long getPackLength() {
        return packLength;
    }
    public void setPackLength(Long packLength) {
        this.packLength = packLength;
    }
    public Long getPackWidth() {
        return packWidth;
    }
    public void setPackWidth(Long packWidth) {
        this.packWidth = packWidth;
    }
    public Long getPackHeight() {
        return packHeight;
    }
    public void setPackHeight(Long packHeight) {
        this.packHeight = packHeight;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}