package com.masiis.shop.dao.mall.shop;

import com.masiis.shop.dao.po.SfShopSku;

import java.util.List;

/**
 * Created by wangbingjian on 2016/4/10.
 */
public interface SfShopSkuExtendMapper {

    List<SfShopSku> selectShopviewByShopId(Long shopId);

}
