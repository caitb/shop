package com.masiis.shop.api.bean.family;

import com.masiis.shop.api.bean.base.BasePagingRes;
import com.masiis.shop.dao.beans.family.ComBrandForFamily;
import com.masiis.shop.dao.beans.product.SkuInfoaAPP;

import java.util.List;

/**
 * Created by jiajinghao on 2016/8/3.
 * 业务:品牌pickList
 */
public class FamilyRes extends BasePagingRes {

    private List<ComBrandForFamily> comBrandForFamilies;//品牌信息list

    private List<SkuInfoaAPP> skuInfoaAPPs;//商品属性



    public List<ComBrandForFamily> getComBrandForFamilies() {
        return comBrandForFamilies;
    }

    public void setComBrandForFamilies(List<ComBrandForFamily> comBrandForFamilies) {
        this.comBrandForFamilies = comBrandForFamilies;
    }

    public List<SkuInfoaAPP> getSkuInfoaAPPs() {
        return skuInfoaAPPs;
    }

    public void setSkuInfoaAPPs(List<SkuInfoaAPP> skuInfoaAPPs) {
        this.skuInfoaAPPs = skuInfoaAPPs;
    }

}
