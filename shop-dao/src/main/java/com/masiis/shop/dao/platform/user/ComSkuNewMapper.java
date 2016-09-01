/*
 * ComSkuNewMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-08-05 Created
 */
package com.masiis.shop.dao.platform.user;

import com.masiis.shop.dao.po.ComSkuNew;

import java.util.List;
import java.util.Map;

public interface ComSkuNewMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ComSkuNew record);

    ComSkuNew selectByPrimaryKey(Integer id);

    List<ComSkuNew> selectAll();

    int updateByPrimaryKey(ComSkuNew record);

    /**
     * 条件查询
     * @param conditionMap
     * @return
     */
    List<Map<String, Object>> selectByCondition(Map<String, Object> conditionMap);

    ComSkuNew selectBySkuId(Integer skuId);
}