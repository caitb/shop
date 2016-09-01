package com.masiis.shop.api.bean.family;

import com.masiis.shop.api.bean.base.BasePagingReq;

/**
 * Created by wangbingjian on 2016/8/12.
 */
public class FamilyHomeListReq extends BasePagingReq {

    /**
     * 1:我創建的
     * 2:我加入的
     */
    private Integer flag;

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}
