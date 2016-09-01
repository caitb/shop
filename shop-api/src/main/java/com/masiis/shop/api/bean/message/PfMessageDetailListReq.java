package com.masiis.shop.api.bean.message;

import com.masiis.shop.api.bean.base.BasePagingReq;

/**
 * @Date 2016/8/2
 * @Author lzh
 */
public class PfMessageDetailListReq extends BasePagingReq {
    private Long uid;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }
}
