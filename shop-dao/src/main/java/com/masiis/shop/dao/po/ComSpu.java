/*
 * ComSpu.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-03 Created
 */
package com.masiis.shop.dao.po;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品spu表
 * 
 * @author masiis
 * @version 1.0 2016-03-03
 */
public class ComSpu {

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 名字
     */
    private String name;

    /**
     * 品牌id
     */
    private Integer brandId;

    private Date createTime;

    private Long createMan;

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

    private Integer categoryId;

    private String categoryName;

    private Integer status;

    /**
     * 广告语
     */
    private String slogan;

    private Integer isSale;

    private Integer isDelete;

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

    /**
     * 试用运费
     */
    private Long shipAmount;

    /**
     * 货号
     * @return
     */
    private String artNo;

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

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
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
        this.categoryName = categoryName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
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
        this.content = content;
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
        this.remark = remark;
    }

    public Long getShipAmount() {
        return shipAmount;
    }

    public void setShipAmount(Long shipAmount) {
        this.shipAmount = shipAmount;
    }

    public String getArtNo() {
        return artNo;
    }

    public void setArtNo(String artNo) {
        this.artNo = artNo;
    }

    @Override
    public String toString() {
        return "ComSpu{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brandId=" + brandId +
                ", createTime=" + createTime +
                ", createMan=" + createMan +
                ", upTime=" + upTime +
                ", downTime=" + downTime +
                ", modifyTime=" + modifyTime +
                ", modifyMan=" + modifyMan +
                ", categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", status=" + status +
                ", slogan='" + slogan + '\'' +
                ", isSale=" + isSale +
                ", isDelete=" + isDelete +
                ", isTrial=" + isTrial +
                ", content='" + content + '\'' +
                ", weight=" + weight +
                ", packLength=" + packLength +
                ", packWidth=" + packWidth +
                ", packHeight=" + packHeight +
                ", remark='" + remark + '\'' +
                ", shipAmount=" + shipAmount +
                ", artNo='" + artNo + '\'' +
                '}';
    }
}