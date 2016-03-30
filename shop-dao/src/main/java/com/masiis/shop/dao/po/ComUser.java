/*
 * ComUser.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-03 Created
 */
package com.masiis.shop.dao.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表（包括代理和分销用户）
 *
 * @author masiis
 * @version 1.0 2016-03-03
 */
public class ComUser implements Serializable {

    private static final long serialVersionUID = -2543606660018018096L;

    private Long id;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 微信用户唯一标识
     */
    private String openid;
    /**
     * 微信昵称
     */
    private String wxNkName;
    /**
     * 微信号
     */
    private String wxId;
    /**
     * 微信头像url
     */
    private String wxHeadImg;
    /**
     * 微信access_token
     */
    private String accessToken;
    /**
     * 微信refresh_token
     */
    private String refreshToken;
    /**
     * access_token过期时间
     */
    private Date atokenExpire;
    /**
     * refreshtoken过期时间
     */
    private Date rtokenExpire;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 身份证号
     */
    private String idCard;
    /**
     * 身份证正面
     */
    private String idCardFrontUrl;
    /**
     * 身份证背面
     */
    private String idCardBackUrl;
    /**
     * 发货省id
     */
    private Integer provinceId;
    /**
     * 发货省名称
     */
    private String provinceName;
    /**
     * 发货市id
     */
    private Integer cityId;
    /**
     * 发货市名称
     */
    private String cityName;
    /**
     * 发货区id
     */
    private Integer regionId;
    /**
     * 发货区名称
     */
    private String regionName;
    /**
     * 发货地址
     */
    private String address;
    /**
     * 拿货方式(0未选择1平台代发2自己发货)
     */
    private Integer sendType;
    /**
     * 是否合伙人(0否1是)
     */
    private Integer isAgent;
    /**
     * 是否认证(0否1是)
     */
    private Integer isVerified;
    /**
     * 性别(0女1男)
     */
    private Integer sex;

    /*审核状态*/
    private Integer auditStatus;
    /*审核通过或不通过原因*/
    private String auditReason;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public String getWxNkName() {
        return wxNkName;
    }

    public void setWxNkName(String wxNkName) {
        this.wxNkName = wxNkName == null ? null : wxNkName.trim();
    }

    public String getWxId() {
        return wxId;
    }

    public void setWxId(String wxId) {
        this.wxId = wxId == null ? null : wxId.trim();
    }

    public String getWxHeadImg() {
        return wxHeadImg;
    }

    public void setWxHeadImg(String wxHeadImg) {
        this.wxHeadImg = wxHeadImg == null ? null : wxHeadImg.trim();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken == null ? null : accessToken.trim();
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken == null ? null : refreshToken.trim();
    }

    public Date getAtokenExpire() {
        return atokenExpire;
    }

    public void setAtokenExpire(Date atokenExpire) {
        this.atokenExpire = atokenExpire;
    }

    public Date getRtokenExpire() {
        return rtokenExpire;
    }

    public void setRtokenExpire(Date rtokenExpire) {
        this.rtokenExpire = rtokenExpire;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard == null ? null : idCard.trim();
    }

    public String getIdCardFrontUrl() {
        return idCardFrontUrl;
    }

    public void setIdCardFrontUrl(String idCardFrontUrl) {
        this.idCardFrontUrl = idCardFrontUrl == null ? null : idCardFrontUrl.trim();
    }

    public String getIdCardBackUrl() {
        return idCardBackUrl;
    }

    public void setIdCardBackUrl(String idCardBackUrl) {
        this.idCardBackUrl = idCardBackUrl == null ? null : idCardBackUrl.trim();
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName == null ? null : provinceName.trim();
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName == null ? null : cityName.trim();
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName == null ? null : regionName.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Integer getSendType() {
        return sendType;
    }

    public void setSendType(Integer sendType) {
        this.sendType = sendType;
    }

    public Integer getIsAgent() {
        return isAgent;
    }

    public void setIsAgent(Integer isAgent) {
        this.isAgent = isAgent;
    }

    public Integer getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(Integer isVerified) {
        this.isVerified = isVerified;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }


    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getAuditReason() {
        return auditReason;
    }

    public void setAuditReason(String auditReason) {
        this.auditReason = auditReason;
    }

    @Override
    public String toString() {
        return "ComUser{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", openid='" + openid + '\'' +
                ", wxNkName='" + wxNkName + '\'' +
                ", wxId='" + wxId + '\'' +
                ", wxHeadImg='" + wxHeadImg + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", atokenExpire=" + atokenExpire +
                ", rtokenExpire=" + rtokenExpire +
                ", realName='" + realName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", idCard='" + idCard + '\'' +
                ", idCardFrontUrl='" + idCardFrontUrl + '\'' +
                ", idCardBackUrl='" + idCardBackUrl + '\'' +
                ", provinceId=" + provinceId +
                ", provinceName='" + provinceName + '\'' +
                ", cityId=" + cityId +
                ", cityName='" + cityName + '\'' +
                ", regionId=" + regionId +
                ", regionName='" + regionName + '\'' +
                ", address='" + address + '\'' +
                ", isAgent=" + isAgent +
                ", sendType=" + sendType +
                ", isVerified=" + isVerified +
                ", sex=" + sex +
                ", sendType=" + sendType +
                ", isVerified=" + isVerified +
                ", auditStatus=" + auditStatus +
                ", auditReason='" + auditReason + '\'' +
                '}';
    }
}