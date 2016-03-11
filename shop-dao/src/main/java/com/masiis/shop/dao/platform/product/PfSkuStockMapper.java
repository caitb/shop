package com.masiis.shop.dao.platform.product;

import com.masiis.shop.dao.po.PfSkuStock;

import java.util.List;

/**
 * Created by cai_tb on 16/3/10.
 */
public interface PfSkuStockMapper {

    PfSkuStock selectById(Integer id);

    List<PfSkuStock> selectByCondition(PfSkuStock pfSkuStock);

    List<PfSkuStock> selectAll();

    void insert(PfSkuStock pfSkuStock);

    void updateById(PfSkuStock pfSkuStock);

    void deleteById(Integer id);

    PfSkuStock selectBySkuId(Integer skuId);
}
