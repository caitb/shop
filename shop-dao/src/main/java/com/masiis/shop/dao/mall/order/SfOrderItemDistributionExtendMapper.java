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

public interface SfOrderItemDistributionExtendMapper {

    List<SfOrderItemDistribution> selectCommissionRecordByUserId(Long userId);

}