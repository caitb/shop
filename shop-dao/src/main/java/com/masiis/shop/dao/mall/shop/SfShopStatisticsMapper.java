/*
 * SfShopStatisticsMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-06-04 Created
 */
package com.masiis.shop.dao.mall.shop;


import com.masiis.shop.dao.po.SfShopStatistics;

import java.util.List;

public interface SfShopStatisticsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SfShopStatistics record);

    SfShopStatistics selectByPrimaryKey(Long id);

    SfShopStatistics selectByShopUserId(Long shopUserId);

    List<SfShopStatistics> selectAll();

    int updateByPrimaryKey(SfShopStatistics record);
}