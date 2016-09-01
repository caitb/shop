package com.masiis.shop.api.bean.user.recommend;

import com.masiis.shop.api.bean.base.BaseRes;
import com.masiis.shop.dao.beans.recommend.MyRecommendPo;

/**
 * Created by wangbingjian on 2016/8/1.
 */
public class MyRecommendRes extends BaseRes{

    private MyRecommendPo myRecommendPo;

    public MyRecommendPo getMyRecommendPo() {
        return myRecommendPo;
    }

    public void setMyRecommendPo(MyRecommendPo myRecommendPo) {
        this.myRecommendPo = myRecommendPo;
    }
}
