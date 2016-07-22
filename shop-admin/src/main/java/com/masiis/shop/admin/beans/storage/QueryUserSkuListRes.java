package com.masiis.shop.admin.beans.storage;

import com.masiis.shop.admin.beans.base.BaseRes;
import com.masiis.shop.dao.beans.user.UserSkuInfo;

import java.util.List;

/**
 * @Date 2016/7/20
 * @Author lzh
 */
public class QueryUserSkuListRes extends BaseRes {
    private List<UserSkuInfo> skus;

    public List<UserSkuInfo> getSkus() {
        return skus;
    }

    public void setSkus(List<UserSkuInfo> skus) {
        this.skus = skus;
    }
}
