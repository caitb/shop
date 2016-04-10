/*
 * SfShopCart.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-04-08 Created
 */
package com.masiis.shop.dao.po;

import java.util.Date;

public class SfShopCart {

    private Long id;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 小铺id
     */
    private Long sfShopId;
    /**
     * 小铺归属人id
     */
    private Long sfShopUserId;

    /**
     * spu主键id
     */
    private Integer spuId;

    /**
     * sku主键id
     */
    private Integer skuId;
    /**
     * 数量
     */
    private Integer quantity;
    /**
     * 购物车内的商品排列顺序
     */
    private Integer sort;
    /**
     * 是否已选中(0:否 1:是)
     */
    private Integer isCheck;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Long getSfShopId() {
        return sfShopId;
    }
    public void setSfShopId(Long sfShopId) {
        this.sfShopId = sfShopId;
    }
    public Long getSfShopUserId() {
        return sfShopUserId;
    }
    public void setSfShopUserId(Long sfShopUserId) {
        this.sfShopUserId = sfShopUserId;
    }
    public Integer getSkuId() {
        return skuId;
    }
    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public Integer getSort() {
        return sort;
    }
    public void setSort(Integer sort) {
        this.sort = sort;
    }
    public Integer getIsCheck() {
        return isCheck;
    }
    public void setIsCheck(Integer isCheck) {
        this.isCheck = isCheck;
    }

    public Integer getSpuId() {
        return spuId;
    }

    public void setSpuId(Integer spuId) {
        this.spuId = spuId;
    }
}