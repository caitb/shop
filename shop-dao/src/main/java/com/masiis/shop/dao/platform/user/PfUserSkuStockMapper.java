/*
 * PfUserSkuStockMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-16 Created
 */
package com.masiis.shop.dao.platform.user;

import com.masiis.shop.dao.po.PfUserSkuStock;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PfUserSkuStockMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PfUserSkuStock record);

    PfUserSkuStock selectByPrimaryKey(Long id);

    List<PfUserSkuStock> selectAll();

    int updateByPrimaryKey(PfUserSkuStock record);

    PfUserSkuStock selectByUserIdAndSkuId(@Param("userId") Long userId, @Param("skuId") Integer skuId);
}