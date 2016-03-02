/*
 * ComUser.java
 * Copyright(C) 2014-2016 ��ʿ����
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao;

import java.util.Date;

/**
 * �û�����������ͷ����û���
 * 
 * @author masiis
 * @version 1.0 2016-03-02
 */
public class ComUser {

    private Long id;
    /**
     * ����ʱ��
     */
    private Date createTime;
    /**
     * ΢���û�Ψһ��ʶ
     */
    private String openid;
    /**
     * ΢���ǳ�
     */
    private String wxNkName;
    /**
     * ΢�ź�
     */
    private String wxId;
    /**
     * ΢��ͷ��url
     */
    private String wxHeadImg;
    /**
     * ΢��access_token
     */
    private String accessToken;
    /**
     * ΢��refresh_token
     */
    private String refreshToken;
    /**
     * access_token����ʱ��
     */
    private Date atokenExpire;
    /**
     * refreshtoken����ʱ��
     */
    private Date rtokenExpire;
    /**
     * ��ʵ����
     */
    private String realName;
    /**
     * �ֻ���
     */
    private String mobile;
    /**
     * ���֤��
     */
    private String idCard;
    /**
     * ���֤����
     */
    private String idCardFrontUrl;
    /**
     * ���֤����
     */
    private String idCardBackUrl;
    /**
     * ����ʡid
     */
    private Integer provinceId;
    /**
     * ����ʡ����
     */
    private String provinceName;
    /**
     * ������id
     */
    private Integer cityId;
    /**
     * ����������
     */
    private String cityName;
    /**
     * ������id
     */
    private Integer regionId;
    /**
     * ����������
     */
    private String regionName;
    /**
     * ������ַ
     */
    private String address;
    /**
     * �Ƿ������(0��1��)
     */
    private Integer isAgent;
    /**
     * �Ա�(0Ů1��)
     */
    private Integer sex;

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
    public Integer getIsAgent() {
        return isAgent;
    }
    public void setIsAgent(Integer isAgent) {
        this.isAgent = isAgent;
    }
    public Integer getSex() {
        return sex;
    }
    public void setSex(Integer sex) {
        this.sex = sex;
    }
}