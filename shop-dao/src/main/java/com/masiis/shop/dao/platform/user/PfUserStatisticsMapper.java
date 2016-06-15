/*
 * PfUserStatisticsMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-06-04 Created
 */
package com.masiis.shop.dao.platform.user;


import com.masiis.shop.dao.po.PfUserStatistics;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PfUserStatisticsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PfUserStatistics record);

    PfUserStatistics selectByPrimaryKey(Long id);

    /**
     * 推荐金额
     * @author muchaofeng
     * @date 2016/6/15 10:50
     */
    PfUserStatistics selectFeeByUserId(Long userId);

    PfUserStatistics selectByUserIdAndSkuId(@Param("userId")Long userId, @Param("skuId")Integer skuId);

    List<PfUserStatistics> selectAll();

    int updateByPrimaryKey(PfUserStatistics record);

    int updateByIdAndVersion(PfUserStatistics statistics);
}