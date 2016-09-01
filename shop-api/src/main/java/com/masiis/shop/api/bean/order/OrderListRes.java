package com.masiis.shop.api.bean.order;

import com.masiis.shop.api.bean.base.BasePagingRes;

import java.util.List;
import java.util.Map;

/**
 * Created by hw on 2016/8/3.
 */
public class OrderListRes extends BasePagingRes {

    private List<Map<String, Object>> orderList;
    private String imgUrlPrefix;

    public String getImgUrlPrefix() {
        return imgUrlPrefix;
    }

    public void setImgUrlPrefix(String imgUrlPrefix) {
        this.imgUrlPrefix = imgUrlPrefix;
    }

    public List<Map<String, Object>> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Map<String, Object>> orderList) {
        this.orderList = orderList;
    }
}
