/*
 * SfUserShareParamMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-05-07 Created
 */
package com.masiis.shop.dao.mall.user;

import com.masiis.shop.dao.po.SfUserShareParam;
import java.util.List;

public interface SfUserShareParamMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SfUserShareParam record);

    SfUserShareParam selectByPrimaryKey(Long id);

    List<SfUserShareParam> selectAll();

    int updateByPrimaryKey(SfUserShareParam record);
}