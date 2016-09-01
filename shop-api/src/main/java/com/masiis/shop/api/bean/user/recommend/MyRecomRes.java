package com.masiis.shop.api.bean.user.recommend;

import com.masiis.shop.api.bean.base.BaseRes;
import com.masiis.shop.dao.beans.user.RecommendedMen;


/**
 * Created by wangbingjian on 2016/8/3.
 */
public class MyRecomRes extends BaseRes{

    private RecommendedMen recommendedMens;

    public RecommendedMen getRecommendedMens() {
        return recommendedMens;
    }

    public void setRecommendedMens(RecommendedMen recommendedMens) {
        this.recommendedMens = recommendedMens;
    }
}
