package com.masiis.shop.dao.beans.promotion;

import com.masiis.shop.dao.po.SfTurnTableGift;

/**
 * Created by hzz on 2016/7/29.
 */
public class TurnTableGiftInfo extends SfTurnTableGift {

    private Integer turnTableGiftId;
    private Integer turnTableRuleId;  //转盘规则id
    private String  giftName;//奖品姓名
    private String imgUrl; //奖品图片url
    private Long userTurnTableRecordId;//这个奖品的中奖纪录

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getTurnTableRuleId() {
        return turnTableRuleId;
    }

    public void setTurnTableRuleId(Integer turnTableRuleId) {
        this.turnTableRuleId = turnTableRuleId;
    }

    public Long getUserTurnTableRecordId() {
        return userTurnTableRecordId;
    }

    public void setUserTurnTableRecordId(Long userTurnTableRecordId) {
        this.userTurnTableRecordId = userTurnTableRecordId;
    }

    public Integer getTurnTableGiftId() {
        return turnTableGiftId;
    }

    public void setTurnTableGiftId(Integer turnTableGiftId) {
        this.turnTableGiftId = turnTableGiftId;
    }
}
