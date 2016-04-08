/*
 * SfOrderItemDistributionMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-04-08 Created
 */
package com.masiis.shop.dao.mall.order;

import com.masiis.shop.dao.po.SfOrderItemDistribution;
import java.util.List;

public interface SfOrderItemDistributionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SfOrderItemDistribution record);

    SfOrderItemDistribution selectByPrimaryKey(Long id);

    List<SfOrderItemDistribution> selectAll();

    int updateByPrimaryKey(SfOrderItemDistribution record);

    int selectCountByCondition(SfOrderItemDistribution record);

    List<SfOrderItemDistribution> selectCommissionRecordByUserId(Long userId);
}