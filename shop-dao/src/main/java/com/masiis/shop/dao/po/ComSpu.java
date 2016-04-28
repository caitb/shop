/*
 * ComSpu.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-30 Created
 */
package com.masiis.shop.dao.po;

import java.math.BigDecimal;
import java.util.Date;

public class ComSpu {

    private Integer id;
    /**
     * spu名称
     */
    private String name;
    /**
     * 品牌id
     */
    private Integer brandId;
    /**
     * 创建日期
     */
    private Date createTime;
    /**
     * 创建人
     */
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
     * 一句话介绍
     */
    private String slogan;
    /**
     * 上下架标志(0下架1上架)
     */
    private Integer isSale;
    /**
     * 删除标志(0否1是)
     */
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
     * 试用运费
     */
    private BigDecimal shipAmount;
    /**
     * 货号
     */
    private String artNo;
    /**
     * 计量单位
     */
    private Integer unit;
    /**
     * 备注
     */
    private String remark;

    /**
     * 商业政策
     */
    private String policy;

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
        this.categoryName = categoryName == null ? null : categoryName.trim();
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
        this.slogan = slogan == null ? null : slogan.trim();
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
    public BigDecimal getShipAmount() {
        return shipAmount;
    }
    public void setShipAmount(BigDecimal shipAmount) {
        this.shipAmount = shipAmount;
    }
    public String getArtNo() {
        return artNo;
    }
    public void setArtNo(String artNo) {
        this.artNo = artNo == null ? null : artNo.trim();
    }
    public Integer getUnit() {
        return unit;
    }
    public void setUnit(Integer unit) {
        this.unit = unit;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
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
                ", shipAmount=" + shipAmount +
                ", artNo='" + artNo + '\'' +
                ", unit=" + unit +
                ", remark='" + remark + '\'' +
                ", policy='" + policy + '\'' +
                '}';
    }
}