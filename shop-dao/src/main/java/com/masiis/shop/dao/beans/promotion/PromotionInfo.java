package com.masiis.shop.dao.beans.promotion;

import java.util.List;

/**
 *  活动信息
 */
public class PromotionInfo {
    private Integer promoId;    //活动id
    private Boolean isMeetPromoRequire; //是否满足活动要求 (用户展示页面不同要求)
    private Integer presonType;     //活动对象
    private String beginTime;       //活动开始时间
    private String endTime;         //活动结束时间
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
    public Integer getPresonType() {
        return presonType;
    }

    public void setPresonType(Integer presonType) {
        this.presonType = presonType;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
