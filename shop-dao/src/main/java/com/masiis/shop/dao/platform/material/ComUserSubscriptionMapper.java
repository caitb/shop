/*
 * ComUserSubscriptionMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-07-04 Created
 */
package com.masiis.shop.dao.platform.material;


import com.masiis.shop.dao.po.ComUserSubscription;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ComUserSubscriptionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ComUserSubscription record);

    ComUserSubscription selectByPrimaryKey(Long id);

    List<ComUserSubscription> selectAll();

    int updateByPrimaryKey(ComUserSubscription record);

    ComUserSubscription selectByUserIdAndMaterialId(@Param("userId") Long userId,@Param("comSkuMaterialLibrary") Integer comSkuMaterialLibrary);
}