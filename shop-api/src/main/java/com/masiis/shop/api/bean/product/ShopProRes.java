package com.masiis.shop.api.bean.product;

import com.masiis.shop.api.bean.base.BaseRes;
import com.masiis.shop.dao.mallBeans.SkuInfo;

import java.util.List;

/**
 * Created by JingHao on 2016/5/26 0026.
 */
public class ShopProRes extends BaseRes{

    private List<SkuInfo> skuInfoList;

    public List<SkuInfo> getSkuInfoList() {
        return skuInfoList;
    }
    public void setSkuInfoList(List<SkuInfo> skuInfoList) {
        this.skuInfoList = skuInfoList;
    }
    private Long shopId;

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }
}
