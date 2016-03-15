/*
 * PfBorderItemMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-08 Created
 */
package com.masiis.shop.dao.platform.order;

import com.masiis.shop.dao.po.PfBorderItem;
import java.util.List;

public interface PfBorderItemMapper {
    int deleteById(Long id);

    int insert(PfBorderItem pfBorderItem);

    PfBorderItem selectById(Long id);

    List<PfBorderItem> selectByCondition(PfBorderItem pfBorderItem);

    int updateById(PfBorderItem pfBorderItem);

    List<PfBorderItem> selectAllByOrderId(Long pfBorderId);
}