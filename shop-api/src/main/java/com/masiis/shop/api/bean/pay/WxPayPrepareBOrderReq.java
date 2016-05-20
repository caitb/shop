package com.masiis.shop.api.bean.pay;

import com.masiis.shop.api.bean.base.BaseBusinessReq;

/**
 * @Date 2016/5/19
 * @Auther lzh
 */
public class WxPayPrepareBOrderReq extends BaseBusinessReq {
    private String orderCode;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }
}
