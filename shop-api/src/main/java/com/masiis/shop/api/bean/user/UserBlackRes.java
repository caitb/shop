package com.masiis.shop.api.bean.user;

import com.masiis.shop.api.bean.base.BaseBusinessRes;

/**
 * Created by cai_tb on 16/9/6.
 */
public class UserBlackRes extends BaseBusinessRes {

    private Boolean isBlack;

    public Boolean getBlack() {
        return isBlack;
    }

    public void setBlack(Boolean black) {
        isBlack = black;
    }

    @Override
    public String toString() {
        return "UserBlackRes{" +
                "isBlack=" + isBlack +
                '}';
    }
}
