package com.masiis.shop.api.bean.order;

import com.masiis.shop.api.bean.base.BaseBusinessReq;

/**
 * @Date 2016/5/5
 * @Auther lzh
 */
public class BorderDeliverReq extends BaseBusinessReq {
    private String shipManName;
    private Long  orderId;
    private String freight;
    private String shipManId;

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getFreight() {
        return freight;
    }

    public String getShipManId() {
        return shipManId;
    }

    public String getShipManName() {
        return shipManName;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setShipManName(String shipManName) {
        this.shipManName = shipManName;
    }

    public void setShipManId(String shipManId) {
        this.shipManId = shipManId;
    }
}
