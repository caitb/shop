/*
 * ComSkuMaterialLibraryMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-07-04 Created
 */
package com.masiis.shop.dao.platform.material;


import com.masiis.shop.dao.po.ComSkuMaterialLibrary;

import java.util.List;

public interface ComSkuMaterialLibraryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ComSkuMaterialLibrary record);

    ComSkuMaterialLibrary selectByPrimaryKey(Integer id);

    List<ComSkuMaterialLibrary> selectAll();

    int updateByPrimaryKey(ComSkuMaterialLibrary record);

    int countLibrary(Long userId);

    List<ComSkuMaterialLibrary> selectAllByPfUserSkuAgent(Long userId);
}