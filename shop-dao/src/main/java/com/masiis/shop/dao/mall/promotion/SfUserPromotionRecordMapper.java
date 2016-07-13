/*
 * SfUserPromotionRecordMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-07-04 Created
 */
package com.masiis.shop.dao.mall.promotion;

import com.masiis.shop.dao.po.SfUserPromotionRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SfUserPromotionRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SfUserPromotionRecord record);

    SfUserPromotionRecord selectByPrimaryKey(Long id);

    List<SfUserPromotionRecord> selectAll();

    int updateByPrimaryKey(SfUserPromotionRecord record);

    SfUserPromotionRecord getPromoRecordByUserIdAndPromoIdAndRuleId(@Param("userId") Long userId,@Param("promoId")  Integer promoId, @Param("promoRuleId") Integer promoRuleId);
}