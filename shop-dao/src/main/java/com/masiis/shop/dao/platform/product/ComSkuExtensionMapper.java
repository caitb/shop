package com.masiis.shop.dao.platform.product;

import com.masiis.shop.dao.po.ComSkuExtension;

import java.util.List;

/**
 * Created by cai_tb on 16/3/23.
 */
public interface ComSkuExtensionMapper {

    ComSkuExtension selectById(Integer id);

    ComSkuExtension selectBySkuId(Integer skuId);

    List<ComSkuExtension> selectByCondition(ComSkuExtension comSkuExtension);

    void insert(ComSkuExtension comSkuExtension);

    void updateById(ComSkuExtension comSkuExtension);

    void deleteById(Integer id);
}
