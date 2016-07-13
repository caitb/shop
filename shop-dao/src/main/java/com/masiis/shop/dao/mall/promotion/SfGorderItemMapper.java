/*
 * SfGorderItemMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-07-04 Created
 */
package com.masiis.shop.dao.mall.promotion;

import com.masiis.shop.dao.po.SfGorderItem;
import java.util.List;

public interface SfGorderItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SfGorderItem record);

    SfGorderItem selectByPrimaryKey(Long id);

    List<SfGorderItem> selectAll();

    int updateByPrimaryKey(SfGorderItem record);
}