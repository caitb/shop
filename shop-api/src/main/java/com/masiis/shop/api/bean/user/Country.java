package com.masiis.shop.api.bean.user;

/**
 * Created by hzz on 2016/5/21.
 */
public class Country {
    private Integer id;
    private Integer pid;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
