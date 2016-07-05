package com.masiis.shop.dao.beans.promotion;

/**
 * Created by hzz on 2016/7/5.
 */
public class PromotionGiftInfo {

    private Integer promoGiftId; //礼物id
    private Integer giftId;
    private String giftName;
    private Integer giftQuantity; //奖品数量


    public Integer getPromoGiftId() {
        return promoGiftId;
    }

    public void setPromoGiftId(Integer promoGiftId) {
        this.promoGiftId = promoGiftId;
    }

    public Integer getGiftId() {
        return giftId;
    }

    public void setGiftId(Integer giftId) {
        this.giftId = giftId;
    }

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public Integer getGiftQuantity() {
        return giftQuantity;
    }

    public void setGiftQuantity(Integer giftQuantity) {
        this.giftQuantity = giftQuantity;
    }
}
