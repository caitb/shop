package com.masiis.shop.api.bean.product;

import com.masiis.shop.api.bean.base.BaseReq;

/**
 * Created by JingHao on 2016/5/19 0019.
 */
public class ApplyProReq extends BaseReq{

    private String token;

    private Long id;//pfUserSkuStockId

    private Long selectedAddressId;//地址id

    private Integer stock;//库存数量

    private String  message; //留言信息

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSelectedAddressId() {
        return selectedAddressId;
    }

    public void setSelectedAddressId(Long selectedAddressId) {
        this.selectedAddressId = selectedAddressId;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
