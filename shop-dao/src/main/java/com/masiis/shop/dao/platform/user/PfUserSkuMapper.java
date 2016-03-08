/*
 * PfUserSkuMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-03 Created
 */
package com.masiis.shop.dao.platform.user;

import com.masiis.shop.dao.po.PfUserSku;
import java.util.List;

public interface PfUserSkuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PfUserSku record);

    PfUserSku selectByPrimaryKey(Integer id);

    List<PfUserSku> selectAll();

    int updateByPrimaryKey(PfUserSku record);

    PfUserSku selectByUserIdAndSkuId(Long userId,Integer skuId);
}