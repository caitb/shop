package com.masiis.shop.dao.beans.promotion;

/**
 * Created by hzz on 2016/7/5.
 */
public class PromotionGiftInfo {

    private Integer promoGiftId; //礼物id
    private Integer giftId;
    private String giftName;
    private Integer giftQuantity; //奖品数量
    private Boolean isEnoughQuantity;  //是否还存在商品
    private Integer sendedQuantity;   //已发放奖品的数量
    private Integer noSendQuantity;  //未发送奖品的数量
    private Integer maxQuantity;    //奖品数量上限
    private String giftImageUrl;    //奖品图片地址


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

    public Integer getSendedQuantity() {
        return sendedQuantity;
    }

    public void setSendedQuantity(Integer sendedQuantity) {
        this.sendedQuantity = sendedQuantity;
    }

    public Integer getNoSendQuantity() {
        return noSendQuantity;
    }

    public void setNoSendQuantity(Integer noSendQuantity) {
        this.noSendQuantity = noSendQuantity;
    }

    public Integer getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(Integer maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public Boolean getIsEnoughQuantity() {
        return isEnoughQuantity;
    }

    public void setIsEnoughQuantity(Boolean enoughQuantity) {
        isEnoughQuantity = enoughQuantity;
    }

    public String getGiftImageUrl() {
        return giftImageUrl;
    }

    public void setGiftImageUrl(String giftImageUrl) {
        this.giftImageUrl = giftImageUrl;
    }
}
