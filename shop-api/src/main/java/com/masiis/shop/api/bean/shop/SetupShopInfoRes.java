package com.masiis.shop.api.bean.shop;

import com.masiis.shop.api.bean.base.BaseRes;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.SfShop;

/**
 * Created by huangwei on 2016/7/28.
 */
public class SetupShopInfoRes extends BaseRes {

    private ComUser comUser;
    private SfShop sfShop;

    private String imageUrl;
    private String rootDir;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRootDir() {
        return rootDir;
    }

    public void setRootDir(String rootDir) {
        this.rootDir = rootDir;
    }

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


}
