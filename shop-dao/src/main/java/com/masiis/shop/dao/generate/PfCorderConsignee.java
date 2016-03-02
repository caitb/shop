/*
 * PfCorderConsignee.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao.generate;

import java.util.Date;

/**
 * 平台分销订单收货人信息表
 * 
 * @author masiis
 * @version 1.0 2016-03-02
 */
public class PfCorderConsignee {

    private Long id;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 订单号
     */
    private Long pfCorderId;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 收货人名称
     */
    private String consignee;
    /**
     * 收货人手机号
     */
    private String mobile;
    /**
     * 省id
     */
    private Integer provinceId;
    /**
     * 省名称
     */
    private String provinceName;
    /**
     * 市id
     */
    private Integer cityId;
    /**
     * 市名称
     */
    private String cityName;
    /**
     * 区id
     */
    private Integer regionId;
    /**
     * 区名称
     */
    private String regionName;
    /**
     * 具体地址
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