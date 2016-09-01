package com.masiis.shop.api.bean.message;

import com.masiis.shop.api.bean.base.BasePagingReq;

/**
 * @Date 2016/8/4
 * @Author lzh
 */
public class ShopMessageDetailReq extends BasePagingReq {
    private Long fUserId;

    public Long getfUserId() {
        return fUserId;
    }

    public void setfUserId(Long fUserId) {
        this.fUserId = fUserId;
    }
}
