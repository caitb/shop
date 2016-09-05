package com.masiis.shop.api.bean.pay;

import com.masiis.shop.api.bean.base.BaseBusinessRes;

/**
 * @Date 2016/8/30
 * @Author lzh
 */
public class GetAlipayOrderStrRes extends BaseBusinessRes {
    private String orderStr;

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }
}
