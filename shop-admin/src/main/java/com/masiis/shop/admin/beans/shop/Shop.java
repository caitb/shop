package com.masiis.shop.admin.beans.shop;

import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.SfShop;

/**
 * Created by cai_tb on 16/4/18.
 */
public class Shop {

    private ComUser comUser;
    private SfShop sfShop;

    public ComUser getComUser() {
        return comUser;
    }

    public void setComUser(ComUser comUser) {
        this.comUser = comUser;
    }

    public SfShop getSfShop() {
        return sfShop;
    }

    public void setSfShop(SfShop sfShop) {
        this.sfShop = sfShop;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "comUser=" + comUser +
                ", sfShop=" + sfShop +
                '}';
    }
}
