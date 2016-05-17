/*
 * ComBrand.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-30 Created
 */
package com.masiis.shop.dao.po;

import java.util.Date;

public class ComBrand {

    private Integer id;
    private Date createTime;
    /**
     * 品牌中文名
     */
    private String cname;
    /**
     * 品牌英文名
     */
    private String ename;
    /**
     * logo标记
     */
    private String logoUrl;
    /**
     * 品牌描述
     */
    private String content;
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

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "ComBrand{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", cname='" + cname + '\'' +
                ", ename='" + ename + '\'' +
                ", logoUrl='" + logoUrl + '\'' +
                ", content='" + content + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}