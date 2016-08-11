/*
 * SfUserTurnTableRecordMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-07-29 Created
 */
package com.masiis.shop.dao.mall.promotion;

import com.masiis.shop.dao.po.SfUserTurnTableRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SfUserTurnTableRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SfUserTurnTableRecord record);

    SfUserTurnTableRecord selectByPrimaryKey(Long id);

    SfUserTurnTableRecord getRecordByUserIdAndTurnTableIdAndGiftId(@Param("userId") Long userId,@Param("turnTableId")  Integer turnTableId,@Param("giftId")  Integer giftId );

    List<SfUserTurnTableRecord> selectAll();

    List<SfUserTurnTableRecord> getRecordInfoByUserId(Long userId);

    List<SfUserTurnTableRecord> getRecordInfoByUserIdAndRuleType(@Param("userId") Long userId,@Param("ruleType") Integer ruleType);

    List<SfUserTurnTableRecord> getRecordByTableId(Integer turnTableId);

    List<SfUserTurnTableRecord> getRecordByTableIdAndType(@Param("turnTableId")Integer turnTableId,@Param("turnTableType")Integer turnTableType);

    int updateByPrimaryKey(SfUserTurnTableRecord record);

    int countUserByTurnTableId(Map<String,Object> conditionMap);

    int countByTurnTableId(Map<String,Object> conditionMap);

    int countByTurnTableIdAndGiftId(Map<String,Object> map);
}