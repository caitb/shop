/*
 * PfUserSkuHistoryMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-06-17 Created
 */
package com.masiis.shop.dao.platform.user;

import com.masiis.shop.dao.po.PfUserSkuHistory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PfUserSkuHistoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PfUserSkuHistory record);

    PfUserSkuHistory selectByPrimaryKey(Integer id);

    List<PfUserSkuHistory> selectAll();

    int updateByPrimaryKey(PfUserSkuHistory record);
}