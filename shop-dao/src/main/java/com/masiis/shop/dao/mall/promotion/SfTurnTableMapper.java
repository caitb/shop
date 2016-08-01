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
import java.util.Map;

public interface SfTurnTableMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SfTurnTable record);

    SfTurnTable selectByPrimaryKey(Integer id);

    List<SfTurnTable> selectAll();

    List<SfTurnTable> getTurnTableByStatus(Integer status);

    int updateByPrimaryKey(SfTurnTable record);

    int update(SfTurnTable record);

    List<SfTurnTable> selectByCondition(Map<String,Object> condition);

}