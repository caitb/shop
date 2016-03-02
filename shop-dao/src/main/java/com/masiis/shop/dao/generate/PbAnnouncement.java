/*
 * PbAnnouncement.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao.generate;

import java.util.Date;

/**
 * 平台公告表

 * 
 * @author masiis
 * @version 1.0 2016-03-02
 */
public class PbAnnouncement {

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
     * 链接地址
     */
    private String hyperlinkUrl;
    /**
     * 备注
     */
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
    public String getHyperlinkUrl() {
        return hyperlinkUrl;
    }
    public void setHyperlinkUrl(String hyperlinkUrl) {
        this.hyperlinkUrl = hyperlinkUrl == null ? null : hyperlinkUrl.trim();
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}