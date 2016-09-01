package com.masiis.shop.api.bean.product;

import com.masiis.shop.api.bean.base.BaseRes;
import com.masiis.shop.dao.beans.product.Product;

import java.util.List;

/**
 * Created by JingHao on 2016/5/19 0019.
 */
public class ProDetailRes extends BaseRes {

    private Product product; //商品详情

    private Integer hasAgent;//代理关系 0:未代理 1 代理过

    private Integer orderStatus;// 订单状态

    private Integer isPrimarySku;//是否主打商品 0 :

    private List<Product> ProductList;//商品列表

    private Integer brandId;//品牌id

    private Integer isBidding;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getHasAgent() {
        return hasAgent;
    }

    public void setHasAgent(Integer hasAgent) {
        this.hasAgent = hasAgent;
    }

    public Integer getIsPrimarySku() {
        return isPrimarySku;
    }

    public void setIsPrimarySku(Integer isPrimarySku) {
        this.isPrimarySku = isPrimarySku;
    }

    public List<Product> getProductList() {
        return ProductList;
    }

    public void setProductList(List<Product> productList) {
        ProductList = productList;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Integer getIsBidding() {
        return isBidding;
    }

    public void setIsBidding(Integer isBidding) {
        this.isBidding = isBidding;
    }
}
