package com.masiis.shop.dao.menu;

public class BUserMenu {
    private Long id;

    private Long userId;

    private Long menuId;

    private String remark;

    public BUserMenu(Long id, Long userId, Long menuId, String remark) {
        this.id = id;
        this.userId = userId;
        this.menuId = menuId;
        this.remark = remark;
    }

    public BUserMenu() {
        super();
    }

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