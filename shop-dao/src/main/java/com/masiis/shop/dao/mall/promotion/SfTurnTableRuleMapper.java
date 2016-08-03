/*
 * SfTurnTableRuleMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-07-29 Created
 */
package com.masiis.shop.dao.mall.promotion;

import com.masiis.shop.dao.po.SfTurnTableRule;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SfTurnTableRuleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SfTurnTableRule record);

    SfTurnTableRule selectByPrimaryKey(Integer id);

    SfTurnTableRule getRuleByTurnTableIdAndType(@Param("turnTableId")Integer turnTableId, @Param("type")Integer type);

    List<SfTurnTableRule> selectAll();

    int updateByPrimaryKey(SfTurnTableRule record);

    List<SfTurnTableRule> listByCondition(Map<String,Object> condition);

    int update(SfTurnTableRule record);

}