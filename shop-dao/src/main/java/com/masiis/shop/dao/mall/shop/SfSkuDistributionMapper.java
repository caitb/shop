/*
 * SfSkuDistributionMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-04-08 Created
 */
package com.masiis.shop.dao.mall.shop;

import com.masiis.shop.dao.po.SfSkuDistribution;
import java.util.List;

public interface SfSkuDistributionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SfSkuDistribution record);

    SfSkuDistribution selectByPrimaryKey(Integer id);

    List<SfSkuDistribution> selectAll();

    int updateByPrimaryKey(SfSkuDistribution record);
}