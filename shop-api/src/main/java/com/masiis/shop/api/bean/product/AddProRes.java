package com.masiis.shop.api.bean.product;

import com.masiis.shop.api.bean.base.BaseRes;
import com.masiis.shop.dao.beans.order.BOrderConfirm;

/**
 * Created by JingHao on 2016/5/24 0024.
 */
public class AddProRes extends BaseRes{

    private Boolean isQueuing;//是否排单

    private Integer count;//排单数量

    private BOrderConfirm bOrderConfirm;//订单确认信息

    public Boolean getIsQueuing() {
        return isQueuing;
    }

    public void setIsQueuing(Boolean isQueuing) {
        this.isQueuing = isQueuing;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public BOrderConfirm getbOrderConfirm() {
        return bOrderConfirm;
    }

    public void setbOrderConfirm(BOrderConfirm bOrderConfirm) {
        this.bOrderConfirm = bOrderConfirm;
    }
}
