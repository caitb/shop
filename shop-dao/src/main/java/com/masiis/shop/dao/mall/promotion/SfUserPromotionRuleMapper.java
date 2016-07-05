/*
 * SfUserPromotionRuleMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-07-04 Created
 */
package com.masiis.shop.dao.mall.promotion;

import com.masiis.shop.dao.po.SfUserPromotionRule;
import java.util.List;

public interface SfUserPromotionRuleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SfUserPromotionRule record);

    SfUserPromotionRule selectByPrimaryKey(Integer id);

    List<SfUserPromotionRule> selectAll();

    int updateByPrimaryKey(SfUserPromotionRule record);
}