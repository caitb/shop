/*
 * SfTurnTableGiftMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-07-29 Created
 */
package com.masiis.shop.dao.mall.promotion;

import com.masiis.shop.dao.po.SfTurnTableGift;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SfTurnTableGiftMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SfTurnTableGift record);

    SfTurnTableGift selectByPrimaryKey(Integer id);

    List<SfTurnTableGift> getTurnTableGiftInfo(@Param("turnTableId") Integer turnTableId, @Param("giftId") Integer giftId);

    SfTurnTableGift getTurnTableGiftInfoByTableIdAndGiftIdAndSort(@Param("turnTableId") Integer turnTableId, @Param("giftId") Integer giftId,@Param("sort") Integer sort);

    List<SfTurnTableGift> selectAll();

    int updateByPrimaryKey(SfTurnTableGift record);

    List<SfTurnTableGift> listByTurnTableId(Integer turnTableId);

    int update(SfTurnTableGift record);


}