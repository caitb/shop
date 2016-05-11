/*
 * SfShopSkuMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-04-08 Created
 */
package com.masiis.shop.dao.mall.shop;

import com.masiis.shop.dao.po.SfShopSku;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SfShopSkuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SfShopSku record);

    SfShopSku selectByPrimaryKey(Long id);

    List<SfShopSku> selectAll();

    int updateByPrimaryKey(SfShopSku record);

    SfShopSku selectByShopIdAndSkuId(@Param("shopId") Long shopId, @Param("skuId") Integer skuId);

    List<SfShopSku> selectByShopId(Long shopId);

    List<SfShopSku> selectByShopIdAndSaleType(@Param("shopId") Long shopId, @Param("isSale") Integer isSale);


}