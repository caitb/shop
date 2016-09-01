package com.masiis.shop.api.bean.message;

import com.masiis.shop.api.bean.base.BaseBusinessRes;
import com.masiis.shop.dao.po.SfShopSku;

import java.util.List;

/**
 * @Date 2016/8/4
 * @Author lzh
 */
public class ShopMessageToNewRes extends BaseBusinessRes {
    private Integer fansNum;
    private Integer spokeManNum;
    private List<SfShopSku> skus;

    public Integer getFansNum() {
        return fansNum;
    }

    public void setFansNum(Integer fansNum) {
        this.fansNum = fansNum;
    }

    public Integer getSpokeManNum() {
        return spokeManNum;
    }

    public void setSpokeManNum(Integer spokeManNum) {
        this.spokeManNum = spokeManNum;
    }

    public List<SfShopSku> getSkus() {
        return skus;
    }

    public void setSkus(List<SfShopSku> skus) {
        this.skus = skus;
    }
}
