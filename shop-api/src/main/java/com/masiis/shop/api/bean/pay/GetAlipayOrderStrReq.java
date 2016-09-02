package com.masiis.shop.api.bean.pay;

import com.masiis.shop.api.bean.base.BaseBusinessReq;

/**
 * @Date 2016/8/30
 * @Author lzh
 */
public class GetAlipayOrderStrReq extends BaseBusinessReq {
    private String orderCode;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }
}
