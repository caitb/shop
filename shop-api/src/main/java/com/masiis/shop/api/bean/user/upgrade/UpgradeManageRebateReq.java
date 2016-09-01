package com.masiis.shop.api.bean.user.upgrade;

import com.masiis.shop.api.bean.base.BaseBusinessReq;

/**
 * Created by wangbingjian on 2016/8/4.
 */
public class UpgradeManageRebateReq extends BaseBusinessReq{

    private Integer pageNum;

    private Integer skuId;
    /**
     * 返利状态0：支付返利
     * 1：获取返利
     * -1：全部
     */
    private Integer status;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
