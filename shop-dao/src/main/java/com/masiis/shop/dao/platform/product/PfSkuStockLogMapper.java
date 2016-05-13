/*
 * PfSkuStockLogMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-05-13 Created
 */
package com.masiis.shop.dao.platform.product;

import com.masiis.shop.dao.po.PfSkuStockLog;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PfSkuStockLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PfSkuStockLog record);

    PfSkuStockLog selectByPrimaryKey(Long id);

    List<PfSkuStockLog> selectAll();

    int updateByPrimaryKey(PfSkuStockLog record);
}