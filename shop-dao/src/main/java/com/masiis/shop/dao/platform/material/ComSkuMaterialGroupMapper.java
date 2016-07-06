/*
 * ComSkuMaterialGroupMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-07-04 Created
 */
package com.masiis.shop.dao.platform.material;


import com.masiis.shop.dao.beans.material.MaterialLibrary;
import com.masiis.shop.dao.po.ComSkuMaterialGroup;

import java.util.List;
import java.util.Map;

public interface ComSkuMaterialGroupMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ComSkuMaterialGroup record);

    ComSkuMaterialGroup selectByPrimaryKey(Integer id);

    List<ComSkuMaterialGroup> selectAll();

    int updateByPrimaryKey(ComSkuMaterialGroup record);

    /**
     * 条件查询素材库
     * @param conditionMap  查询参数
     * @return
     */
    List<MaterialLibrary> selectMaterialLibrary(Map<String, Object> conditionMap);

    /**
     * 条件查询素材库模块
     * @param conditionMap  查询参数
     * @return
     */
    List<ComSkuMaterialGroup> selectMaterialGroup(Map<String, Object> conditionMap);

}