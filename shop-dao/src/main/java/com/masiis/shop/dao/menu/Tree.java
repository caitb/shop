package com.masiis.shop.dao.menu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhaoLiang on 2016/2/19.
 */
public class Tree {

    public Long getMenuid() {
        return menuid;
    }

    public void setMenuid(Long menuid) {
        this.menuid = menuid;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getMenuname() {
        return menuname;
    }

    public void setMenuname(String menuname) {
        this.menuname = menuname;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public List<Tree> getMenus() {
        return menus;
    }

    public void setMenus(List<Tree> menus) {
        this.menus = menus;
    }

    private Long menuid;

    private String icon;

    private String menuname;

    private String url;

    private Long parentId;

    private List<Tree> menus = new ArrayList<>();

}

