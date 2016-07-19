/*
 * PbStorageBillItemMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-07-19 Created
 */
package com.masiis.shop.dao.platform.storage;

import com.masiis.shop.dao.po.PbStorageBillItem;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PbStorageBillItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PbStorageBillItem record);

    PbStorageBillItem selectByPrimaryKey(Integer id);

    List<PbStorageBillItem> selectAll();

    int updateByPrimaryKey(PbStorageBillItem record);
}