package com.masiis.shop.api.bean.user;

import com.masiis.shop.api.bean.base.BasePagingReq;

/**
 * Created by wangbingjian on 2016/5/6.
 */
public class AccountUserIncomeReq extends BasePagingReq{

    /**
     * 格式为yyyy-MM
     */
    private String date;
    /**
     * 小铺、代理标识
     * 0：全部
     * 1：代理
     * 2：小铺
     */
    private Integer flag;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}
