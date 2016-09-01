package com.masiis.shop.api.bean.family;

import com.masiis.shop.api.bean.base.BasePagingRes;
import com.masiis.shop.dao.beans.family.ComBrandForFamily;
import com.masiis.shop.dao.beans.product.SkuInfoaAPP;
import com.masiis.shop.dao.po.PbBanner;
import com.masiis.shop.dao.po.PfUserOrganization;

import java.util.List;

/**
 * Created by jiajinghao on 2016/8/3.
 */
public class WorldRes extends BasePagingRes {

    private List<PbBanner> bannerList; // banner 展示

    private List<ComBrandForFamily> comBrandForFamilies;//品牌信息list

    private List<PfUserOrganization> familyList; //家族信息

    private List<PfUserOrganization> teamList;//团队信息

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

    public List<PfUserOrganization> getFamilyList() {
        return familyList;
    }

    public void setFamilyList(List<PfUserOrganization> familyList) {
        this.familyList = familyList;
    }

    public List<PfUserOrganization> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<PfUserOrganization> teamList) {
        this.teamList = teamList;
    }

    public List<PbBanner> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<PbBanner> bannerList) {
        this.bannerList = bannerList;
    }
}
