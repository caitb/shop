/*
 * SfShopStatisticsMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao.po;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface SfShopStatisticsMapper {
    int countByExample(SfShopStatisticsExample example);

    int deleteByExample(SfShopStatisticsExample example);

    @Insert({
        "insert into sf_shop_statistics (id)",
        "values (#{id,jdbcType=BIGINT})"
    })
    int insert(SfShopStatistics record);

    int insertSelective(SfShopStatistics record);

    List<SfShopStatistics> selectByExample(SfShopStatisticsExample example);

    int updateByExampleSelective(@Param("record") SfShopStatistics record, @Param("example") SfShopStatisticsExample example);

    int updateByExample(@Param("record") SfShopStatistics record, @Param("example") SfShopStatisticsExample example);
}