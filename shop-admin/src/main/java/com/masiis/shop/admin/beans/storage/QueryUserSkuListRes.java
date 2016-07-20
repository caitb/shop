package com.masiis.shop.admin.beans.storage;

import com.masiis.shop.admin.beans.base.BaseRes;
import com.masiis.shop.dao.po.ComSku;

import java.util.List;

/**
 * @Date 2016/7/20
 * @Author lzh
 */
public class QueryUserSkuListRes extends BaseRes {
    private List<ComSku> skus;

    public List<ComSku> getSkus() {
        return skus;
    }

    public void setSkus(List<ComSku> skus) {
        this.skus = skus;
    }
}
