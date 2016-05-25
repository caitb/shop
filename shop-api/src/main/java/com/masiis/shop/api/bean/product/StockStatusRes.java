package com.masiis.shop.api.bean.product;

import com.masiis.shop.api.bean.base.BaseRes;

/**
 * Created by JingHao on 2016/5/25 0025.
 */
public class StockStatusRes extends BaseRes{

     private int status;//库存状态标识 0库存充足1库存不足进入排单2库存不足不可下单

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
