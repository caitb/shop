package com.masiis.shop.dao.platform.product;

import com.masiis.shop.dao.po.PfSkuStock;

/**
 * Created by JingHao on 2016/3/16 0016.
 */
public interface ComSkuStockMapper {

    PfSkuStock selectBySkuId(Integer id);
}
