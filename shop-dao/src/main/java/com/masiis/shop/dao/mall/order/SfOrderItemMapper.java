/*
 * SfOrderItemMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-04-08 Created
 */
package com.masiis.shop.dao.mall.order;

import com.masiis.shop.dao.po.SfOrderItem;
import java.util.List;

public interface SfOrderItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SfOrderItem record);

    SfOrderItem selectByPrimaryKey(Long id);

    List<SfOrderItem> selectAll();

    int updateByPrimaryKey(SfOrderItem record);
}