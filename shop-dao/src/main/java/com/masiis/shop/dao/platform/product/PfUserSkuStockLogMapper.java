/*
 * PfUserSkuStockLogMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-05-13 Created
 */
package com.masiis.shop.dao.platform.product;

import com.masiis.shop.dao.po.PfUserSkuStockLog;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PfUserSkuStockLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PfUserSkuStockLog record);

    PfUserSkuStockLog selectByPrimaryKey(Long id);

    List<PfUserSkuStockLog> selectAll();

    int updateByPrimaryKey(PfUserSkuStockLog record);
}