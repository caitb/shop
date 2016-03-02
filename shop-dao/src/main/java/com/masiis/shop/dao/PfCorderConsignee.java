/*
 * PfCorderConsignee.java
 * Copyright(C) 2014-2016 ��ʿ����
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao;

import java.util.Date;

/**
 * ƽ̨���������ջ�����Ϣ��
 * 
 * @author masiis
 * @version 1.0 2016-03-02
 */
public class PfCorderConsignee {

    private Long id;
    /**
     * ����ʱ��
     */
    private Date createTime;
    /**
     * ������
     */
    private Long pfCorderId;
    /**
     * �û�id
     */
    private Long userId;
    /**
     * �ջ�������
     */
    private String consignee;
    /**
     * �ջ����ֻ���
     */
    private String mobile;
    /**
     * ʡid
     */
    private Integer provinceId;
    /**
     * ʡ����
     */
    private String provinceName;
    /**
     * ��id
     */
    private Integer cityId;
    /**
     * ������
     */
    private String cityName;
    /**
     * ��id
     */
    private Integer regionId;
    /**
     * ������
     */
    private String regionName;
    /**
     * �����ַ
     */
    private String address;
    private String remark;

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
    public Long getPfCorderId() {
        return pfCorderId;
    }
    public void setPfCorderId(Long pfCorderId) {
        this.pfCorderId = pfCorderId;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getConsignee() {
        return consignee;
    }
    public void setConsignee(String consignee) {
        this.consignee = consignee == null ? null : consignee.trim();
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
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
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}