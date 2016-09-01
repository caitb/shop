/*
 * ComBrandCertificate.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-08-18 Created
 */
package com.masiis.shop.dao.po;

import java.util.Date;

public class ComBrandCertificate {

    private Integer id;
    /**
     * 品牌id
     */
    private Integer brandId;
    /**
     * 等级id
     */
    private Integer agentLevelId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建人
     */
    private Long createMan;
    /**
     * 品牌证书背景图片
     */
    private String backimgUrl;
    /**
     * 证书编码X坐标0为取中
     */
    private Integer certificateCodeX;
    private Integer certificateCodeY;
    private Integer certificateDataX;
    private Integer certificateDataY;
    private Integer levelNameX;
    private Integer levelNameY;
    private Integer skuNameX;
    private Integer skuNameY;
    private Integer skuEnameX;
    private Integer skuEnameY;
    private Integer userRealnameX;
    private Integer userRealnameY;
    private Integer userIdcardX;
    private Integer userIdcardY;
    private Integer userMobileX;
    private Integer userMobileY;
    private Integer userWxidX;
    private Integer userWxidY;
    private String remark;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
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
    public String getBackimgUrl() {
        return backimgUrl;
    }
    public void setBackimgUrl(String backimgUrl) {
        this.backimgUrl = backimgUrl == null ? null : backimgUrl.trim();
    }
    public Integer getCertificateCodeX() {
        return certificateCodeX;
    }
    public void setCertificateCodeX(Integer certificateCodeX) {
        this.certificateCodeX = certificateCodeX;
    }
    public Integer getCertificateCodeY() {
        return certificateCodeY;
    }
    public void setCertificateCodeY(Integer certificateCodeY) {
        this.certificateCodeY = certificateCodeY;
    }
    public Integer getCertificateDataX() {
        return certificateDataX;
    }
    public void setCertificateDataX(Integer certificateDataX) {
        this.certificateDataX = certificateDataX;
    }
    public Integer getCertificateDataY() {
        return certificateDataY;
    }
    public void setCertificateDataY(Integer certificateDataY) {
        this.certificateDataY = certificateDataY;
    }
    public Integer getLevelNameX() {
        return levelNameX;
    }
    public void setLevelNameX(Integer levelNameX) {
        this.levelNameX = levelNameX;
    }
    public Integer getLevelNameY() {
        return levelNameY;
    }
    public void setLevelNameY(Integer levelNameY) {
        this.levelNameY = levelNameY;
    }
    public Integer getSkuNameX() {
        return skuNameX;
    }
    public void setSkuNameX(Integer skuNameX) {
        this.skuNameX = skuNameX;
    }
    public Integer getSkuNameY() {
        return skuNameY;
    }
    public void setSkuNameY(Integer skuNameY) {
        this.skuNameY = skuNameY;
    }
    public Integer getSkuEnameX() {
        return skuEnameX;
    }
    public void setSkuEnameX(Integer skuEnameX) {
        this.skuEnameX = skuEnameX;
    }
    public Integer getSkuEnameY() {
        return skuEnameY;
    }
    public void setSkuEnameY(Integer skuEnameY) {
        this.skuEnameY = skuEnameY;
    }
    public Integer getUserRealnameX() {
        return userRealnameX;
    }
    public void setUserRealnameX(Integer userRealnameX) {
        this.userRealnameX = userRealnameX;
    }
    public Integer getUserRealnameY() {
        return userRealnameY;
    }
    public void setUserRealnameY(Integer userRealnameY) {
        this.userRealnameY = userRealnameY;
    }
    public Integer getUserIdcardX() {
        return userIdcardX;
    }
    public void setUserIdcardX(Integer userIdcardX) {
        this.userIdcardX = userIdcardX;
    }
    public Integer getUserIdcardY() {
        return userIdcardY;
    }
    public void setUserIdcardY(Integer userIdcardY) {
        this.userIdcardY = userIdcardY;
    }
    public Integer getUserMobileX() {
        return userMobileX;
    }
    public void setUserMobileX(Integer userMobileX) {
        this.userMobileX = userMobileX;
    }
    public Integer getUserMobileY() {
        return userMobileY;
    }
    public void setUserMobileY(Integer userMobileY) {
        this.userMobileY = userMobileY;
    }
    public Integer getUserWxidX() {
        return userWxidX;
    }
    public void setUserWxidX(Integer userWxidX) {
        this.userWxidX = userWxidX;
    }
    public Integer getUserWxidY() {
        return userWxidY;
    }
    public void setUserWxidY(Integer userWxidY) {
        this.userWxidY = userWxidY;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}