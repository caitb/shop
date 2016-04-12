/*
 * SfShopMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-04-08 Created
 */
package com.masiis.shop.dao.mall.shop;

import com.masiis.shop.dao.po.SfShop;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SfShopMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SfShop record);

    SfShop selectByPrimaryKey(Long id);

    List<SfShop> selectAll();

    int updateByPrimaryKey(SfShop record);

    SfShop selectByUserId(Long userId);

    SfShop selectByUserIdAndShopId(@Param("userId") Long userId,@Param("shopId") Long shopId);
}