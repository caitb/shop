/*
 * PfUserStatisticsMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-06-04 Created
 */
package com.masiis.shop.dao.platform.user;


import com.masiis.shop.dao.po.PfUserStatistics;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PfUserStatisticsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PfUserStatistics record);

    PfUserStatistics selectByPrimaryKey(Long id);

    List<PfUserStatistics> selectAll();

    int updateByPrimaryKey(PfUserStatistics record);

    int updateByIdAndVersion(PfUserStatistics statistics);
}