/*
 * PfUserSkuTestlistMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-06-08 Created
 */
package com.masiis.shop.dao.platform.user;

import com.masiis.shop.dao.po.PfUserSkuTestlist;
import java.util.List;

public interface PfUserSkuTestlistMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PfUserSkuTestlist record);

    PfUserSkuTestlist selectByPrimaryKey(Long id);

    List<PfUserSkuTestlist> selectAll();

    int updateByPrimaryKey(PfUserSkuTestlist record);
}