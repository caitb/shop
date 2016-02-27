/*
 * SysUserMenu.java
 * Copyright(C) 20xx-2015 xxxxxx��˾
 * All rights reserved.
 * -----------------------------------------------
 * 2016-02-24 Created
 */
package com.masiis.shop.dao.usermenu;

/**
 * 
 * 
 * @author ���ܴ���
 * @version 1.0 2016-02-24
 */
public class SysUserMenu {

    private Long id;
    /**
     * �û�id
     */
    private Long userId;
    /**
     * �˵�id
     */
    private Long menuId;
    /**
     * ��ע
     */
    private String remark;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Long getMenuId() {
        return menuId;
    }
    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}