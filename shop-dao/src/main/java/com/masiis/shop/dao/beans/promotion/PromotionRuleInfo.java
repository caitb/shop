package com.masiis.shop.dao.beans.promotion;

import java.util.List;

/**
 *  活动页面--领取奖励信息
 */
public class PromotionRuleInfo {
    private Integer promoRuleId;    //活动规则id
    private Integer status; //奖品是否领取(0粉丝未达到要求 1粉丝达到要求未领取 2已领取 3奖品领取完无法领取)
    private Integer promotionFansQuantity; //活动的粉丝数量
    private Integer needFansQuantity; //距离活动要求还需要的粉丝数量
    //详情信息
    private List<PromotionGiftInfo> giftInfos;


    public Integer getPromotionFansQuantity() {
        return promotionFansQuantity;
    }

    public void setPromotionFansQuantity(Integer promotionFansQuantity) {
        this.promotionFansQuantity = promotionFansQuantity;
    }

    public Integer getNeedFansQuantity() {
        return needFansQuantity;
    }

    public void setNeedFansQuantity(Integer needFansQuantity) {
        this.needFansQuantity = needFansQuantity;
    }

    public List<PromotionGiftInfo> getGiftInfos() {
        return giftInfos;
    }

    public void setGiftInfos(List<PromotionGiftInfo> giftInfos) {
        this.giftInfos = giftInfos;
    }

    public Integer getPromoRuleId() {
        return promoRuleId;
    }
    public void setPromoRuleId(Integer promoRuleId) {
        this.promoRuleId = promoRuleId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
