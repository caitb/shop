/*
 * SfTurnTableGiftMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-07-29 Created
 */
package com.masiis.shop.dao.mall.promotion;

import com.masiis.shop.dao.po.SfTurnTableGift;
import java.util.List;

public interface SfTurnTableGiftMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SfTurnTableGift record);

    SfTurnTableGift selectByPrimaryKey(Integer id);

    List<SfTurnTableGift> selectAll();

    int updateByPrimaryKey(SfTurnTableGift record);
}