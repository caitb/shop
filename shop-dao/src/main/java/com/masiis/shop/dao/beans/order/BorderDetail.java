package com.masiis.shop.dao.beans.order;

import com.masiis.shop.dao.po.PfBorderConsignee;
import com.masiis.shop.dao.po.PfBorderFreight;
import com.masiis.shop.dao.po.PfBorderItem;

import java.util.List;

/**
 * @author muchaofeng
 * @date $date$ $time$
 */

public class BorderDetail {
    private List<PfBorderItem> pfBorderItems;
    private List<PfBorderFreight> pfBorderFreights;//快递公司信息
    private PfBorderConsignee pfBorderConsignee;//收货人

    public List<PfBorderItem> getPfBorderItems() {
        return pfBorderItems;
    }

    public List<PfBorderFreight> getPfBorderFreights() {
        return pfBorderFreights;
    }

    public PfBorderConsignee getPfBorderConsignee() {
        return pfBorderConsignee;
    }

    public void setPfBorderConsignee(PfBorderConsignee pfBorderConsignee) {
        this.pfBorderConsignee = pfBorderConsignee;
    }

    public void setPfBorderFreights(List<PfBorderFreight> pfBorderFreights) {
        this.pfBorderFreights = pfBorderFreights;
    }

    public void setPfBorderItems(List<PfBorderItem> pfBorderItems) {
        this.pfBorderItems = pfBorderItems;
    }
}
