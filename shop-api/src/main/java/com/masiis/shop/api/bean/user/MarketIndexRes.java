package com.masiis.shop.api.bean.user;

import com.masiis.shop.api.bean.base.BaseRes;

import java.util.List;

/**
 * @Date 2016/5/4
 * @Auther lzh
 */
public class MarketIndexRes extends BaseRes {
    private List<String> imgs;
    private List<MarketProItem> pros;
    private Integer proNums;

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }

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
