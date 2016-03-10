/*
 * PfBorderMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-03 Created
 */
package com.masiis.shop.dao.platform.order;

import com.masiis.shop.dao.po.PfBorder;
import java.util.List;

public interface PfBorderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PfBorder record);

    PfBorder selectByPrimaryKey(Long id);

    List<PfBorder> selectAll();

    int updateByPrimaryKey(PfBorder record);

    PfBorder selectByOrderCode(String orderId);
}