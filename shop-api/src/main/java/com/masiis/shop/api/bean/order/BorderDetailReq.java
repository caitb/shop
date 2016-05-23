package com.masiis.shop.api.bean.order;

import com.masiis.shop.api.bean.base.BaseBusinessReq;

/**
 * @Date 2016/5/5
 * @Auther lzh
 */
public class BorderDetailReq extends BaseBusinessReq {
    private Long  id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
