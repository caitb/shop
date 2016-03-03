/*
 * PfBorderItemMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-03 Created
 */
package com.masiis.shop.dao.platform.order;

import com.masiis.shop.dao.po.PfBorderItem;
import java.util.List;

public interface PfBorderItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PfBorderItem record);

    PfBorderItem selectByPrimaryKey(Long id);

    List<PfBorderItem> selectAll();

    int updateByPrimaryKey(PfBorderItem record);
}