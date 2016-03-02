/*
 * ComSpu.java
 * Copyright(C) 2014-2016 ��ʿ����
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao;

import java.util.Date;

/**
 * ��Ʒspu��
 * 
 * @author masiis
 * @version 1.0 2016-03-02
 */
public class ComSpu extends ComSpuKey {

    /**
     * Ʒ��id
     */
    private Integer brandId;
    /**
     * ���һ���ϼ�ʱ��
     */
    private Date upTime;
    /**
     * ���һ���¼�ʱ��
     */
    private Date downTime;
    /**
     * �޸�ʱ��
     */
    private Date modifyTime;
    /**
     * �޸���
     */
    private Long modifyMan;
    /**
     * �����
     */
    private String slogan;
    /**
     * �����ñ�־λ(0��1��)
     */
    private Integer isTrial;
    /**
     * ��Ʒ����
     */
    private String content;
    /**
     * ����
     */
    private Long weight;
    /**
     * ��
     */
    private Long packLength;
    /**
     * ��
     */
    private Long packWidth;
    /**
     * ��
     */
    private Long packHeight;
    /**
     * ��ע
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