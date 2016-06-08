/*
 * PfUserSkuPayrateMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-06-04 Created
 */
package com.masiis.shop.dao.platform.user;


import com.masiis.shop.dao.po.PfUserSkuPayrate;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PfUserSkuPayrateMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PfUserSkuPayrate record);

    PfUserSkuPayrate selectByPrimaryKey(Long id);

    List<PfUserSkuPayrate> selectAll();

    int updateByPrimaryKey(PfUserSkuPayrate record);

    PfUserSkuPayrate selectByUserIdAndSkuId(@Param("userId")Long userId, @Param("skuId")Integer skuId);
}