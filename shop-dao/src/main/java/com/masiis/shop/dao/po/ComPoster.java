/*
 * ComPoster.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-07-18 Created
 */
package com.masiis.shop.dao.po;

import java.util.Date;

public class ComPoster {

    private Long id;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 海报类型(0发展合伙人海报；1B端店铺海报；2C端店铺海报）
     */
    private Integer type;
    /**
     * 创建海报的人
     */
    private Long userId;
    /**
     * 创建海报人的名称
     */
    private String realName;
    /**
     * 海报二维码参数
     */
    private Long shareParamId;
    /**
     * 海报名字
     */
    private String posterName;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getShareParamId() {
        return shareParamId;
    }

    public void setShareParamId(Long shareParamId) {
        this.shareParamId = shareParamId;
    }

    public String getPosterName() {
        return posterName;
    }

    public void setPosterName(String posterName) {
        this.posterName = posterName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    @Override
    public String toString() {
        return "ComPoster{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", type=" + type +
                ", userId=" + userId +
                ", realName='" + realName + '\'' +
                ", shareParamId=" + shareParamId +
                ", posterName='" + posterName + '\'' +
                '}';
    }
}