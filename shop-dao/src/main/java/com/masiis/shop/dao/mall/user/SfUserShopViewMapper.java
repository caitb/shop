/*
 * SfUserShopViewMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-04-08 Created
 */
package com.masiis.shop.dao.mall.user;

import com.masiis.shop.dao.po.SfUserShopView;
import java.util.List;

public interface SfUserShopViewMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SfUserShopView record);

    SfUserShopView selectByPrimaryKey(Long id);

    List<SfUserShopView> selectAll();

    int updateByPrimaryKey(SfUserShopView record);
}