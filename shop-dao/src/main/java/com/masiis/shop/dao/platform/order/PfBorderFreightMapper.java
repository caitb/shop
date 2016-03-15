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

    PfBorderFreight selectById(Long id);

    List<PfBorderFreight> selectByBorderId(Long borderId);

    List<PfBorderFreight> selectByCondition(PfBorderFreight pfBorderFreight);

    int insert(PfBorderFreight pfBorderFreight);

    void updateById(PfBorderFreight pfBorderFreight);

    void deleteById(Long id);

}