/*
 * PfUserShareParamMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-06-14 Created
 */
package com.masiis.shop.dao.platform.user;

import com.masiis.shop.dao.po.PfUserShareParam;
import java.util.List;

public interface PfUserShareParamMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PfUserShareParam record);

    PfUserShareParam selectByPrimaryKey(Long id);

    List<PfUserShareParam> selectAll();

    int updateByPrimaryKey(PfUserShareParam record);
}