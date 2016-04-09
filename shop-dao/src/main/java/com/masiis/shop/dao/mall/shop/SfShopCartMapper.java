/*
 * SfShopCartMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-04-08 Created
 */
package com.masiis.shop.dao.mall.shop;

import com.masiis.shop.dao.po.SfShopCart;
import java.util.List;

public interface SfShopCartMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SfShopCart record);

    SfShopCart selectByPrimaryKey(Long id);

    List<SfShopCart> selectAll();

    int updateByPrimaryKey(SfShopCart record);

    List<SfShopCart>  getShopCartInfoByUserIdAndShopId(Long userId,Long sfShopId);
}