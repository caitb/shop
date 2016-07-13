/*
 * ComSkuMaterialMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-07-04 Created
 */
package com.masiis.shop.dao.platform.material;


import com.masiis.shop.dao.beans.material.Material;
import com.masiis.shop.dao.po.ComSkuMaterial;
import com.masiis.shop.dao.po.ComSkuMaterialItem;

import java.util.List;
import java.util.Map;

public interface ComSkuMaterialMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ComSkuMaterial record);

    ComSkuMaterial selectByPrimaryKey(Integer id);

    List<ComSkuMaterial> selectAll();

    int updateByPrimaryKey(ComSkuMaterial record);

    /**
     * 查找素材
     * @param conditionMap
     * @return
     */
    List<Material> selectMaterialItem(Map<String, Object> conditionMap);

    List<ComSkuMaterial> selectByMglId(Integer materialGroupId);

    List<ComSkuMaterialItem> selectMaterialItemByMtId(Integer materialId);
}