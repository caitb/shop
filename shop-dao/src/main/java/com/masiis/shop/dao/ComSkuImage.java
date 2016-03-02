/*
 * ComSkuImage.java
 * Copyright(C) 2014-2016 ��ʿ����
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao;

import java.util.Date;

/**
 * ��ƷSKUͼƬ��
 * 
 * @author masiis
 * @version 1.0 2016-03-02
 */
public class ComSkuImage {

    private Integer id;
    /**
     * ��������
     */
    private Date createTime;
    /**
     * ������
     */
    private Long createMan;
    /**
     * spu����id
     */
    private Integer spuId;
    /**
     * sku����id
     */
    private Integer skuId;
    /**
     * ͼƬ����
     */
    private String imgName;
    /**
     * ͼƬ��ַ
     */
    private String imgUrl;
    /**
     * �޸�����
     */
    private Date modifyTime;
    /**
     * �޸���
     */
    private Long modifyMan;
    /**
     * Ĭ�ϱ�־(0��1��)
     */
    private Integer isDefault;
    /**
     * ͼƬ��š�SKU���ͼƬչʾ˳������ԽСԽ��ǰ��Ҫ������������
     */
    private Integer sort;
    /**
     * ��ע
     */
    private String remark;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Long getCreateMan() {
        return createMan;
    }
    public void setCreateMan(Long createMan) {
        this.createMan = createMan;
    }
    public Integer getSpuId() {
        return spuId;
    }
    public void setSpuId(Integer spuId) {
        this.spuId = spuId;
    }
    public Integer getSkuId() {
        return skuId;
    }
    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }
    public String getImgName() {
        return imgName;
    }
    public void setImgName(String imgName) {
        this.imgName = imgName == null ? null : imgName.trim();
    }
    public String getImgUrl() {
        return imgUrl;
    }
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }
    public Date getModifyTime() {
        return modifyTime;
    }
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
    public Long getModifyMan() {
        return modifyMan;
    }
    public void setModifyMan(Long modifyMan) {
        this.modifyMan = modifyMan;
    }
    public Integer getIsDefault() {
        return isDefault;
    }
    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }
    public Integer getSort() {
        return sort;
    }
    public void setSort(Integer sort) {
        this.sort = sort;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}