/*
 * PfSkuAgentMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-04 Created
 */
package com.masiis.shop.dao.platform.product;

import com.masiis.shop.dao.po.PfSkuAgent;
import java.util.List;

public interface PfSkuAgentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PfSkuAgent record);

    PfSkuAgent selectByPrimaryKey(Integer id);

    List<PfSkuAgent> selectAll();

    int updateByPrimaryKey(PfSkuAgent record);

    List<PfSkuAgent> selectAllBySkuId(Integer skuId);

    PfSkuAgent selectBySkuIdAndLevelId(Integer skuId,Integer levelId);
}