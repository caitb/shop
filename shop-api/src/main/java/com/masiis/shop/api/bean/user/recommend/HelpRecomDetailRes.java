package com.masiis.shop.api.bean.user.recommend;

import com.masiis.shop.api.bean.base.BaseBusinessRes;
import com.masiis.shop.dao.beans.user.HelpRecommended;


/**
 * Created by wangbingjian on 2016/8/8.
 */
public class HelpRecomDetailRes extends BaseBusinessRes {

    private String skuName;

    private String recommedName;

    private HelpRecommended recommended;

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getRecommedName() {
        return recommedName;
    }

    public void setRecommedName(String recommedName) {
        this.recommedName = recommedName;
    }

    public HelpRecommended getRecommended() {
        return recommended;
    }

    public void setRecommended(HelpRecommended recommended) {
        this.recommended = recommended;
    }

}
