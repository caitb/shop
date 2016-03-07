/*
 * ComAgentLevelMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-04 Created
 */
package com.masiis.shop.dao.platform.product;

import com.masiis.shop.dao.po.ComAgentLevel;
import java.util.List;

public interface ComAgentLevelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ComAgentLevel record);

    ComAgentLevel selectByPrimaryKey(Integer id);

    List<ComAgentLevel> selectAll();

    int updateByPrimaryKey(ComAgentLevel record);
}