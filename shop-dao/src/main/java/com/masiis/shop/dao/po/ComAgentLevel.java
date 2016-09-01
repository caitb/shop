/*
 * ComAgentLevel.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-08-09 Created
 */
package com.masiis.shop.dao.po;

import java.util.Date;

public class ComAgentLevel {

    /**
     * 代理等级
     */
    private Integer id;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 合伙等级名称
     */
    private String name;
    /**
     * 合伙等级别称
     */
    private String anotherName;
    /**
     * 图片地址
     */
    private String imgUrl;
    /**
     * 是否可以创建组织
     */
    private Integer isOrganization;
    /**
     * 组织后缀
     */
    private String organizationSuffix;
    /**
     * 是否前端展示
     */
    private Integer isOrganizationShow;
    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getAnotherName() {
        return anotherName;
    }

    public void setAnotherName(String anotherName) {
        this.anotherName = anotherName == null ? null : anotherName.trim();
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

    public Integer getIsOrganization() {
        return isOrganization;
    }

    public void setIsOrganization(Integer isOrganization) {
        this.isOrganization = isOrganization;
    }

    public String getOrganizationSuffix() {
        return organizationSuffix;
    }

    public void setOrganizationSuffix(String organizationSuffix) {
        this.organizationSuffix = organizationSuffix == null ? null : organizationSuffix.trim();
    }

    public Integer getIsOrganizationShow() {
        return isOrganizationShow;
    }

    public void setIsOrganizationShow(Integer isOrganizationShow) {
        this.isOrganizationShow = isOrganizationShow;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    @Override
    public String toString() {
        return "ComAgentLevel{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", name='" + name + '\'' +
                ", anotherName='" + anotherName + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", isOrganization=" + isOrganization +
                ", organizationSuffix='" + organizationSuffix + '\'' +
                ", isOrganizationShow=" + isOrganizationShow +
                ", remark='" + remark + '\'' +
                '}';
    }
}