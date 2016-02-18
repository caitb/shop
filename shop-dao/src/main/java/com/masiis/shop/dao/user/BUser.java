package com.masiis.shop.dao.user;

public class BUser {
    private Long id;

    private String userName;

    private String trueName;

    private String password;

    private String email;

    private String sex;

    private Integer age;

    private String phone;

    public BUser(Long id, String userName, String trueName, String password, String email, String sex, Integer age, String phone) {
        this.id = id;
        this.userName = userName;
        this.trueName = trueName;
        this.password = password;
        this.email = email;
        this.sex = sex;
        this.age = age;
        this.phone = phone;
    }

    public BUser() {
        super();
    }

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