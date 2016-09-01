package com.masiis.shop.dao.beans.family;

import com.masiis.shop.dao.beans.statistic.BrandStatistic;
import com.masiis.shop.dao.beans.user.CountGroup;

/**
 * 团队列表首页
 * Created by wangbingjian on 2016/8/23.
 */
public class MyTeamListHomePo {

    private Integer skuId;
    /**
     * 商品名称
     */
    private String skuName;
    /**
     * 直接下级人数
     */
    private Integer directNum;
    /**
     * 间接下级人数
     */
    private Integer indirectNum;

    private BrandStatistic brandStatistic;

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public Integer getDirectNum() {
        return directNum;
    }

    public void setDirectNum(Integer directNum) {
        this.directNum = directNum;
    }

    public Integer getIndirectNum() {
        return indirectNum;
    }

    public void setIndirectNum(Integer indirectNum) {
        this.indirectNum = indirectNum;
    }

    public BrandStatistic getBrandStatistic() {
        return brandStatistic;
    }

    public void setBrandStatistic(BrandStatistic brandStatistic) {
        this.brandStatistic = brandStatistic;
    }
}
