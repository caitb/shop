/*
 * PbStorageBillOperationLogMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-07-22 Created
 */
package com.masiis.shop.dao.platform.storage;

import com.masiis.shop.dao.po.PbStorageBillOperationLog;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PbStorageBillOperationLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PbStorageBillOperationLog record);

    PbStorageBillOperationLog selectByPrimaryKey(Long id);

    List<PbStorageBillOperationLog> selectAll();

    int updateByPrimaryKey(PbStorageBillOperationLog record);
}