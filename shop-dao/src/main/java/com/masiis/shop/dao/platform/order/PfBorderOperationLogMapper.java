/*
 * PfBorderOperationLogMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-03 Created
 */
package com.masiis.shop.dao.platform.order;

import com.masiis.shop.dao.po.PfBorderOperationLog;
import java.util.List;

public interface PfBorderOperationLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PfBorderOperationLog record);

    PfBorderOperationLog selectByPrimaryKey(Long id);

    List<PfBorderOperationLog> selectAll();

    int updateByPrimaryKey(PfBorderOperationLog record);
}