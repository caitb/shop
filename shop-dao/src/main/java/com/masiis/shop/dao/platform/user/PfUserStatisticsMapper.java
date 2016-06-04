/*
 * PfUserStatisticsMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-06-04 Created
 */
package com.masiis.shop.dao.platform.user;


import com.masiis.shop.dao.po.PfUserStatistics;

import java.util.List;

public interface PfUserStatisticsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PfUserStatistics record);

    PfUserStatistics selectByPrimaryKey(Long id);

    List<PfUserStatistics> selectAll();

    int updateByPrimaryKey(PfUserStatistics record);
}