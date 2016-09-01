package com.masiis.shop.api.bean.product;

import com.masiis.shop.api.bean.base.BasePagingRes;
import com.masiis.shop.dao.mallBeans.SkuInfo;
import com.masiis.shop.dao.po.SfShop;

import java.util.List;

/**
 * Created by hw on 2016/8/1.
 */
public class RetailProRes extends BasePagingRes {
    private List<SkuInfo> skuInfoList;
    private SfShop sfShop;

    public List<SkuInfo> getSkuInfoList() {
        return skuInfoList;
    }

    public void setSkuInfoList(List<SkuInfo> skuInfoList) {
        this.skuInfoList = skuInfoList;
    }

    public SfShop getSfShop() {
        return sfShop;
    }

    public void setSfShop(SfShop sfShop) {
        this.sfShop = sfShop;
    }
}
