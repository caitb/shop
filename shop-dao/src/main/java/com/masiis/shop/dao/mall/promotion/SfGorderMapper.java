/*
 * SfGorderMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-07-04 Created
 */
package com.masiis.shop.dao.mall.promotion;

import com.masiis.shop.dao.po.SfGorder;
import java.util.List;

public interface SfGorderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SfGorder record);

    SfGorder selectByPrimaryKey(Long id);

    List<SfGorder> selectAll();

    int updateByPrimaryKey(SfGorder record);
}