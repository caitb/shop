package com.masiis.shop.dao.beans.promotion;

import java.util.List;

/**
 *  活动信息
 */
public class PromotionInfo {
    private Integer promoId;    //活动id
    private Boolean isMeetPromoRequire; //是否满足活动要求 (用户展示页面不同要求)
    private Integer fansQuantity;   //粉丝数量
    private List<PromotionRuleInfo> ruleInfos;


    public Integer getPromoId() {
        return promoId;
    }

    public void setPromoId(Integer promoId) {
        this.promoId = promoId;
    }

    public Boolean getMeetPromoRequire() {
        return isMeetPromoRequire;
    }

    public void setMeetPromoRequire(Boolean meetPromoRequire) {
        isMeetPromoRequire = meetPromoRequire;
    }

    public List<PromotionRuleInfo> getRuleInfos() {
        return ruleInfos;
    }

    public void setRuleInfos(List<PromotionRuleInfo> ruleInfos) {
        this.ruleInfos = ruleInfos;
    }

    public Integer getFansQuantity() {
        return fansQuantity;
    }

    public void setFansQuantity(Integer fansQuantity) {
        this.fansQuantity = fansQuantity;
    }
}
