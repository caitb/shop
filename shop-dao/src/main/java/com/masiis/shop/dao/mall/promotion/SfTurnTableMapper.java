/*
 * SfTurnTableMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-07-29 Created
 */
package com.masiis.shop.dao.mall.promotion;

import com.masiis.shop.dao.po.SfTurnTable;
import java.util.List;

public interface SfTurnTableMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SfTurnTable record);

    SfTurnTable selectByPrimaryKey(Integer id);

    List<SfTurnTable> selectAll();

    int updateByPrimaryKey(SfTurnTable record);
}