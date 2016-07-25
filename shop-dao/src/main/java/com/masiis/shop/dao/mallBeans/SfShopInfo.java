package com.masiis.shop.dao.mallBeans;

import java.util.List;

/**
 * Created by jiajinghao on 2016/7/25.
 */
public class SfShopInfo {

    private List<SfShopDetail> sfShopDetailList;//业务数据

    private  List<String> skuUrlList;// 业务图片

    public List<String> getSkuUrlList() {
        return skuUrlList;
    }

    public void setSkuUrlList(List<String> skuUrlList) {
        this.skuUrlList = skuUrlList;
    }

    public List<SfShopDetail> getSfShopDetailList() {
        return sfShopDetailList;
    }

    public void setSfShopDetailList(List<SfShopDetail> sfShopDetailList) {
        this.sfShopDetailList = sfShopDetailList;
    }
}
