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

public interface SfUserTurnTableRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SfUserTurnTableRecord record);

    SfUserTurnTableRecord selectByPrimaryKey(Long id);

    SfUserTurnTableRecord getRecordByUserIdAndTurnTableIdAndGiftId(@Param("userId") Long userId,@Param("turnTableId")  Integer turnTableId,@Param("giftId")  Integer giftId );

    List<SfUserTurnTableRecord> selectAll();

    List<SfUserTurnTableRecord> getRecordInfoByUserId(Long userId);

    int updateByPrimaryKey(SfUserTurnTableRecord record);
}