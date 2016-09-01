package com.masiis.shop.api.bean.user.upgrade;

import com.masiis.shop.api.bean.base.BaseBusinessReq;

/**
 * Created by wangbingjian on 2016/8/3.
 */
public class UpgradeManageLowerReq extends BaseBusinessReq{

    private Integer pageNum;

    private Integer skuId;
    /**
     * "0":"未处理",
     * "1":"处理中",
     * "2":"待支付",
     * "3":"完成",
     * "4":"取消"
     8 -1 ：全部
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
