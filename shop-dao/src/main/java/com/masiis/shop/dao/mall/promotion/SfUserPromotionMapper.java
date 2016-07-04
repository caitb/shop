/*
 * SfUserPromotionMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-07-04 Created
 */
package com.masiis.shop.dao.mall.promotion;

import com.masiis.shop.dao.po.SfUserPromotion;
import java.util.List;

public interface SfUserPromotionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SfUserPromotion record);

    SfUserPromotion selectByPrimaryKey(Integer id);

    List<SfUserPromotion> selectAll();

    int updateByPrimaryKey(SfUserPromotion record);
}