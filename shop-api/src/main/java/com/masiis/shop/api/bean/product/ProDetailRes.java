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

    private List<Product> productList;//商品列表

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

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public Integer getHasAgent() {
        return hasAgent;
    }

    public void setHasAgent(Integer hasAgent) {
        this.hasAgent = hasAgent;
    }
}
