/*
 * ComAgentLevelMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-04 Created
 */
package com.masiis.shop.dao.platform.product;

import com.masiis.shop.dao.po.ComAgentLevel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ComAgentLevelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ComAgentLevel record);

    ComAgentLevel selectByPrimaryKey(Integer id);

    List<ComAgentLevel> selectAll();

    int updateByPrimaryKey(ComAgentLevel record);

    int getMaxAgentLevel();

    List<ComAgentLevel> selectByIds(List<Integer> ids);

    ComAgentLevel selectUseByPrimaryKey(Integer id);

    List<ComAgentLevel> selectImgUrlById(@Param("userId") Long id);
}