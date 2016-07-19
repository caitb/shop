/*
 * PbStorageBillMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-07-19 Created
 */
package com.masiis.shop.dao.platform.storage;

import com.masiis.shop.dao.po.PbStorageBill;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PbStorageBillMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PbStorageBill record);

    PbStorageBill selectByPrimaryKey(Integer id);

    List<PbStorageBill> selectAll();

    int updateByPrimaryKey(PbStorageBill record);

    List<Map<String, Object>> storagechangeList(Map<String, Object> conditionMap);
}