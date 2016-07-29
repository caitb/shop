package com.masiis.shop.dao.beans.promotion;

import com.masiis.shop.dao.po.SfTurnTableGift;

/**
 * Created by hzz on 2016/7/29.
 */
public class TurnTableGiftInfo extends SfTurnTableGift {

    private String  giftName;//奖品姓名
    private String imgUrl; //奖品图片url

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
}
