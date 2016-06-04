/*
 * SfUserStatisticsMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-06-04 Created
 */
package com.masiis.shop.dao.mall.user;


import com.masiis.shop.dao.po.SfUserStatistics;

import java.util.List;

public interface SfUserStatisticsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SfUserStatistics record);

    SfUserStatistics selectByPrimaryKey(Long id);

    List<SfUserStatistics> selectAll();

    int updateByPrimaryKey(SfUserStatistics record);
}