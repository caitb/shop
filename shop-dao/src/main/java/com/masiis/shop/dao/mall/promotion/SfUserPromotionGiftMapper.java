/*
 * SfUserPromotionGiftMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-07-04 Created
 */
package com.masiis.shop.dao.mall.promotion;

import com.masiis.shop.dao.po.SfUserPromotionGift;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SfUserPromotionGiftMapper {
    int deleteByPrimaryKey(Integer id);

    int deleteByRuleId(Integer ruleId);

    int insert(SfUserPromotionGift record);

    SfUserPromotionGift selectByPrimaryKey(Integer id);

    Integer maxId();

    List<SfUserPromotionGift> selectAll();

    List<SfUserPromotionGift> getPromoGiftByPromoIdAndRuleId(@Param("promoId") Integer promoId, @Param("promoRuleId") Integer promoRuleId);

    List<SfUserPromotionGift> getPromoGiftByPromoId(Integer promoId);

    SfUserPromotionGift getPromoGiftByPromoIdAndRuleIdAndGiftValue(@Param("promoId") Integer promoId, @Param("promoRuleId") Integer promoRuleId, @Param("giftValue") Integer giftValue);

    int updateByPrimaryKey(SfUserPromotionGift record);

}