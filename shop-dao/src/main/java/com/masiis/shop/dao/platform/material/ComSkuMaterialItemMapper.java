/*
 * ComSkuMaterialItemMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-07-04 Created
 */
package com.masiis.shop.dao.platform.material;


import com.masiis.shop.dao.po.ComSkuMaterialItem;

import java.util.List;

public interface ComSkuMaterialItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ComSkuMaterialItem record);

    ComSkuMaterialItem selectByPrimaryKey(Integer id);

    List<ComSkuMaterialItem> selectAll();

    int updateByPrimaryKey(ComSkuMaterialItem record);
}