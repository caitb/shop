/*
 * ComBank.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-19 Created
 */
package com.masiis.shop.dao.po;

public class ComBank {

    /**
     * id
     */
    private Integer id;
    /**
     * 银行名称
     */
    private String bankName;
    /**
     * 银行图片路径
     */
    private String bankImg;
    /**
     * 银行客服电话
     */
    private String bankMobile;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getBankName() {
        return bankName;
    }
    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }
    public String getBankImg() {
        return bankImg;
    }
    public void setBankImg(String bankImg) {
        this.bankImg = bankImg == null ? null : bankImg.trim();
    }
    public String getBankMobile() {
        return bankMobile;
    }
    public void setBankMobile(String bankMobile) {
        this.bankMobile = bankMobile == null ? null : bankMobile.trim();
    }
}