package com.masiis.shop.web.platform.beans.user;

import java.io.Serializable;

/**
 * Created by ZhaoLiang on 2016/3/2.
 */
public class Student implements Serializable{
    private String username;
    private String age;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
