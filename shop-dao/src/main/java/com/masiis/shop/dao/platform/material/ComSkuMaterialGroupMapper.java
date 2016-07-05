/*
 * ComSkuMaterialGroupMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-07-04 Created
 */
package com.masiis.shop.dao.platform.material;


import com.masiis.shop.dao.po.ComSkuMaterialGroup;

import java.util.List;

public interface ComSkuMaterialGroupMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ComSkuMaterialGroup record);

    ComSkuMaterialGroup selectByPrimaryKey(Integer id);

    List<ComSkuMaterialGroup> selectAll();

    int updateByPrimaryKey(ComSkuMaterialGroup record);
}