package com.masiis.shop.api.bean.product;

import com.masiis.shop.api.bean.base.BaseRes;
import com.masiis.shop.api.bean.user.MarketProItem;

import java.util.List;

/**
 * @Date 2016/5/4
 * @Auther lzh
 */
public class ProAllListRes extends BaseRes {
    private List<MarketProItem> pros;
    private Integer proNums;

    public List<MarketProItem> getPros() {
        return pros;
    }

    public void setPros(List<MarketProItem> pros) {
        this.pros = pros;
    }

    public Integer getProNums() {
        return proNums;
    }

    public void setProNums(Integer proNums) {
        this.proNums = proNums;
    }
}
