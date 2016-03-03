/*
 * SfShopStatisticsMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-03 Created
 */
package po;

import com.masiis.shop.dao.SfShopStatistics;
import org.apache.ibatis.annotations.Insert;

public interface SfShopStatisticsMapper {
    @Insert({
        "insert into sf_shop_statistics (id)",
        "values (#{id,jdbcType=BIGINT})"
    })
    int insert(SfShopStatistics record);

    int insertSelective(SfShopStatistics record);
}