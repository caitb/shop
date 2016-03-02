/*
 * ComDictionary.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao.generate;

/**
 * 字典表
 * 
 * @author masiis
 * @version 1.0 2016-03-02
 */
public class ComDictionary {

    private Integer id;
    /**
     * 标识码
     */
    private String code;
    /**
     * 键
     */
    private Integer key;
    /**
     * 值
     */
    private String value;
    /**
     * 备注
     */
    private String remark;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }
    public Integer getKey() {
        return key;
    }
    public void setKey(Integer key) {
        this.key = key;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}