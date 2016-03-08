package com.masiis.shop.dao.beans.product;

/**
 * ProductSimple
 *
 * @author ZhaoLiang
 * @date 2016/3/7
 */
public class ProductSimple {
    /**
     * skuId
     */
    private Integer skuId;
    /**
     * 商品名称
     */
    private String skuName;
    /**
     * 商品展示图片
     */
    private String skuDefaultImgURL;
    /**
     * 一句话介绍
     */
    private String slogan;

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

    public String getSkuDefaultImgURL() {
        return skuDefaultImgURL;
    }

    public void setSkuDefaultImgURL(String skuDefaultImgURL) {
        this.skuDefaultImgURL = skuDefaultImgURL;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }
}
