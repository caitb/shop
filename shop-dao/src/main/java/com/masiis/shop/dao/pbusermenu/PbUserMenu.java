package com.masiis.shop.dao.pbusermenu;

import java.util.Date;

public class PbUserMenu {
    private Long id;

    private Long pbUserId;

    private Long pbMenuId;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPbUserId() {
        return pbUserId;
    }

    public void setPbUserId(Long pbUserId) {
        this.pbUserId = pbUserId;
    }

    public Long getPbMenuId() {
        return pbMenuId;
    }

    public void setPbMenuId(Long pbMenuId) {
        this.pbMenuId = pbMenuId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}