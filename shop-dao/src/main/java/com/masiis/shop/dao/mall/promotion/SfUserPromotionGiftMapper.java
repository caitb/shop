/*
 * SfUserPromotionGiftMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-07-04 Created
 */
package com.masiis.shop.dao.mall.promotion;

import com.masiis.shop.dao.po.SfUserPromotionGift;
import java.util.List;

public interface SfUserPromotionGiftMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SfUserPromotionGift record);

    SfUserPromotionGift selectByPrimaryKey(Integer id);

    List<SfUserPromotionGift> selectAll();

    int updateByPrimaryKey(SfUserPromotionGift record);
}