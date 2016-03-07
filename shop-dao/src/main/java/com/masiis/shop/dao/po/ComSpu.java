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

    /**
     * 试用运费
     */
    private Long shipAmount;

    /**
     * 货号
     * @return
     */
    private String artNo;

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
        this.slogan = slogan;
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
                "brandId=" + brandId +
                ", upTime=" + upTime +
                ", downTime=" + downTime +
                ", modifyTime=" + modifyTime +
                ", modifyMan=" + modifyMan +
                ", slogan='" + slogan + '\'' +
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