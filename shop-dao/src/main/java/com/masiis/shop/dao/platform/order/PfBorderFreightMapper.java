/*
 * PfBorderFreightMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-03 Created
 */
package com.masiis.shop.dao.platform.order;

import com.masiis.shop.dao.po.PfBorderFreight;
import java.util.List;

public interface PfBorderFreightMapper {
    int insert(PfBorderFreight record);

    List<PfBorderFreight> selectAll();
}