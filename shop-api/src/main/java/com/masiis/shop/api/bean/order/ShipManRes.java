package com.masiis.shop.api.bean.order;

import com.masiis.shop.api.bean.base.BaseBusinessRes;
import com.masiis.shop.dao.po.ComShipMan;

import java.util.List;

/**
 * Created by hw on 2016/8/15.
 */
public class ShipManRes extends BaseBusinessRes {

    private List<ComShipMan> comShipMans;

    public List<ComShipMan> getComShipMans() {
        return comShipMans;
    }

    public void setComShipMans(List<ComShipMan> comShipMans) {
        this.comShipMans = comShipMans;
    }
}
