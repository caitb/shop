package com.masiis.shop.dao.beans.user;

import com.masiis.shop.dao.po.SfShopSku;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by wangbingjian on 2016/4/10.
 */
public class SfUserShopViewExtend {

    /**
     * 小铺名称
     */
    private String shopName;
    /**
     * 小铺描述
     */
    private String explanation;
    /**
     * 用户账户保证金W
     */
    private BigDecimal bailFee;
    /**
     * 已浏览的天数
     */
    private Integer days;
    /**
     * 小铺logo
     */
    private String logo;
    /**
     * 用户平台代理级别List
     */
    private List<SfShopSku> shopSkus;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public BigDecimal getBailFee() {
        return bailFee;
    }

    public void setBailFee(BigDecimal bailFee) {
        this.bailFee = bailFee;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public List<SfShopSku> getShopSkus() {
        return shopSkus;
    }

    public void setShopSkus(List<SfShopSku> shopSkus) {
        this.shopSkus = shopSkus;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
