package com.masiis.shop.api.bean.family;

import com.masiis.shop.api.bean.base.BasePagingRes;
import com.masiis.shop.dao.beans.product.SkuInfoaAPP;
import com.masiis.shop.dao.po.ComBrand;

import java.util.List;

/**
 * Created by jiajinghao on 2016/8/9.
 */
public class BrandOfSkuRes extends BasePagingRes {

    private List<SkuInfoaAPP> skuInfoaAPPs;//商品属性

    private ComBrand comBrand;//品牌

    public List<SkuInfoaAPP> getSkuInfoaAPPs() {
        return skuInfoaAPPs;
    }

    public void setSkuInfoaAPPs(List<SkuInfoaAPP> skuInfoaAPPs) {
        this.skuInfoaAPPs = skuInfoaAPPs;
    }

    public ComBrand getComBrand() {
        return comBrand;
    }

    public void setComBrand(ComBrand comBrand) {
        this.comBrand = comBrand;
    }
}
