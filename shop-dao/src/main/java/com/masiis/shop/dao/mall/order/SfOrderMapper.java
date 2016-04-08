/*
 * SfOrderMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-04-08 Created
 */
package com.masiis.shop.dao.mall.order;

import com.masiis.shop.dao.po.SfOrder;

import java.util.List;

public interface SfOrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SfOrder record);

    SfOrder selectByPrimaryKey(Long id);

    List<SfOrder> selectAll();

    int updateByPrimaryKey(SfOrder record);
}