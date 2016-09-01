package com.masiis.shop.api.bean.product;

import com.masiis.shop.api.bean.base.BasePagingReq;

/**
 * Created by hw on 2016/8/1.
 */
public class RetailProReq extends BasePagingReq {

    private Long shopId;
    private Integer isSale;//1代理中，0仓库中
    private Integer deliverType;//1店主发货，0平台代发，null全部
    private Long shopSkuId;//sf_shop_sku 主键
    private Integer stock;//库存
    private Integer skuId;

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public Long getShopSkuId() {
        return shopSkuId;
    }

    public void setShopSkuId(Long shopSkuId) {
        this.shopSkuId = shopSkuId;
    }

    public Integer getDeliverType() {
        return deliverType;
    }

    public void setDeliverType(Integer deliverType) {
        this.deliverType = deliverType;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Integer getIsSale() {
        return isSale;
    }

    public void setIsSale(Integer isSale) {
        this.isSale = isSale;
    }

}
