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
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PfUserSkuStockMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PfUserSkuStock record);

    PfUserSkuStock selectByPrimaryKey(Long id);

    List<PfUserSkuStock> selectAll();

    //int updateById(PfUserSkuStock pfUserSkuStock);////没有设置乐观锁不建议使用

    PfUserSkuStock selectByUserIdAndSkuId(@Param("userId") Long userId, @Param("skuId") Integer skuId);

    int updateByIdAndVersion(PfUserSkuStock pfUserSkuStock);
}