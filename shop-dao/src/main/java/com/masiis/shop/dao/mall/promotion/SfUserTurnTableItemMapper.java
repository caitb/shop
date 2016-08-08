/*
 * SfUserTurnTableItemMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-07-29 Created
 */
package com.masiis.shop.dao.mall.promotion;

import com.masiis.shop.dao.po.SfUserTurnTableItem;
import java.util.List;

public interface SfUserTurnTableItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SfUserTurnTableItem record);

    SfUserTurnTableItem selectByPrimaryKey(Long id);

    List<SfUserTurnTableItem> selectAll();

    int updateByPrimaryKey(SfUserTurnTableItem record);
}