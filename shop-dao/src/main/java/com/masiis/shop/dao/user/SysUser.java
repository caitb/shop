/*
 * SysUser.java
 * Copyright(C) 20xx-2015 xxxxxx��˾
 * All rights reserved.
 * -----------------------------------------------
 * 2016-02-24 Created
 */
package com.masiis.shop.dao.user;

/**
 * 
 * 
 * @author ���ܴ���
 * @version 1.0 2016-02-24
 */
public class SysUser {

    private Long id;
    /**
     * �û���
     */
    private String userName;
    /**
     * ��ʵ����
     */
    private String trueName;
    private String password;
    private String email;
    /**
     * �Ա�
     */
    private String sex;
    /**
     * ����
     */
    private Integer age;
    /**
     * �ֻ���
     */
    private String phone;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }
    public String getTrueName() {
        return trueName;
    }
    public void setTrueName(String trueName) {
        this.trueName = trueName == null ? null : trueName.trim();
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }
}