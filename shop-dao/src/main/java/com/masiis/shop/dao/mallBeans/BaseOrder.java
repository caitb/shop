package com.masiis.shop.dao.mallBeans;

import com.masiis.shop.dao.po.PfBorderConsignee;
import com.masiis.shop.dao.po.PfBorderItem;
import com.masiis.shop.dao.po.SfOrderItem;

import java.util.List;

/**
 * @author muchaofeng
 * @date $date$ $time$
 */

public class BaseOrder {
    private String imgUrl;//图片地址
    /**
     * 数量
     */
    private Integer totalQuantity = 0;
    private String pidUserName;
    /**
     * 订单商品
     */
    private List<SfOrderItem> SfOrderItems;
    //private PfBorderConsignee pfBorderConsignee;

    public void setPidUserName(String pidUserName) {
        this.pidUserName = pidUserName;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public List<SfOrderItem> getSfOrderItems() {
        return SfOrderItems;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setSfOrderItems(List<SfOrderItem> sfOrderItems) {
        SfOrderItems = sfOrderItems;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getPidUserName() {
        return pidUserName;
    }

}
