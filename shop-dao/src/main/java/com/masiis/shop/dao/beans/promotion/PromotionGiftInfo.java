package com.masiis.shop.dao.beans.promotion;

/**
 * Created by hzz on 2016/7/5.
 */
public class PromotionGiftInfo {

    private Integer promoGiftId; //礼物id
    private Integer skuId;
    private String skuName;
    private Integer skuQuantity; //奖品数量


    public Integer getPromoGiftId() {
        return promoGiftId;
    }

    public void setPromoGiftId(Integer promoGiftId) {
        this.promoGiftId = promoGiftId;
    }

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

    public Integer getSkuQuantity() {
        return skuQuantity;
    }

    public void setSkuQuantity(Integer skuQuantity) {
        this.skuQuantity = skuQuantity;
    }

}
