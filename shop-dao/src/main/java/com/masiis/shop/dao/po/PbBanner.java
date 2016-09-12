/*
 * PbBanner.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-03 Created
 */
package com.masiis.shop.dao.po;

import java.util.Date;

/**
 * 平台后台运营banner表
 * 
 * @author masiis
 * @version 1.0 2016-03-03
 */
public class PbBanner {

    private Integer id;
    /**
     * 创建时间
     */

    private Date createTime;
    /**
     * 名称
     */
    private String name;
    /**
     * 图片地址
     */
    private String imgUrl;
    /**
     * 链接地址
     */
    private String hyperlinkUrl;
    /**
     * 备注
     */
    private String remark;

    private Integer sort;

    /**
     * app 链接地址
     */
    private String appHyperlinkUrl;

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
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getHyperlinkUrl() {
        return hyperlinkUrl;
    }

    public void setHyperlinkUrl(String hyperlinkUrl) {
        this.hyperlinkUrl = hyperlinkUrl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getAppHyperlinkUrl() {
        return appHyperlinkUrl;
    }

    public void setAppHyperlinkUrl(String appHyperlinkUrl) {
        this.appHyperlinkUrl = appHyperlinkUrl;
    }

    @Override
    public String toString() {
        return "PbBanner{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", name='" + name + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", hyperlinkUrl='" + hyperlinkUrl + '\'' +
                ", appHyperlinkUrl='" + appHyperlinkUrl + '\'' +
                ", remark='" + remark + '\'' +
                ", sort=" + sort +
                '}';
    }
}