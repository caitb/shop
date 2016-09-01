package com.masiis.shop.api.bean.user.recommend;

import com.masiis.shop.api.bean.base.BaseReq;

/**
 * Created by wangbingjian on 2016/8/1.
 */
public class MyRecommendReq extends BaseReq {

    private String token;
    /**
     * 当前页码
     */
    private Integer pageNum;
    /**
     * 查询tab  0：收入奖励订单  1：发出奖励订单
     */
    private Integer tab;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getTab() {
        return tab;
    }

    public void setTab(Integer tab) {
        this.tab = tab;
    }
}
