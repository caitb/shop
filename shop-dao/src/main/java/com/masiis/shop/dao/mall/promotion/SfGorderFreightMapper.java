/*
 * SfGorderFreightMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-07-04 Created
 */
package com.masiis.shop.dao.mall.promotion;

import com.masiis.shop.dao.po.SfGorderFreight;
import java.util.List;

public interface SfGorderFreightMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SfGorderFreight record);

    SfGorderFreight selectByPrimaryKey(Long id);

    List<SfGorderFreight> selectAll();

    int updateByPrimaryKey(SfGorderFreight record);

    SfGorderFreight selectByGorderId(Long gorderId);
}