/*
 * SfGorderOperationLogMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-07-04 Created
 */
package com.masiis.shop.dao.mall.promotion;

import com.masiis.shop.dao.po.SfGorderOperationLog;
import java.util.List;

public interface SfGorderOperationLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SfGorderOperationLog record);

    SfGorderOperationLog selectByPrimaryKey(Long id);

    List<SfGorderOperationLog> selectAll();

    int updateByPrimaryKey(SfGorderOperationLog record);
}