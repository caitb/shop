/*
 * SfShopShoutLogMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-04-08 Created
 */
package com.masiis.shop.dao.mall.shop;

import com.masiis.shop.dao.po.SfShopShoutLog;
import java.util.List;

public interface SfShopShoutLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SfShopShoutLog record);

    SfShopShoutLog selectByPrimaryKey(Long id);

    List<SfShopShoutLog> selectAll();

    int updateByPrimaryKey(SfShopShoutLog record);
}