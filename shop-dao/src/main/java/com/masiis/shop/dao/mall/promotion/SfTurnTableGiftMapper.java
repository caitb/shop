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

    SfTurnTableGift getTurnTableGiftInfo(@Param("turnTableId") Integer turnTableId, @Param("giftId") Integer giftId);

    SfTurnTableGift getTurnTableGiftInfoByTableIdAndGiftIdAndStatus(@Param("turnTableId") Integer turnTableId, @Param("giftId") Integer giftId,@Param("status") Integer status);

    List<SfTurnTableGift> selectAll();

    int updateByPrimaryKey(SfTurnTableGift record);
}