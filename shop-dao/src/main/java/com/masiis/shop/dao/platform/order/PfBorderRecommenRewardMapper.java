/*
 * PfBorderRecommenRewardMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-06-14 Created
 */
package com.masiis.shop.dao.platform.order;

import com.masiis.shop.dao.po.PfBorderRecommenReward;
import java.util.List;

public interface PfBorderRecommenRewardMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PfBorderRecommenReward record);

    PfBorderRecommenReward selectByPrimaryKey(Long id);

    List<PfBorderRecommenReward> selectAll();

    int updateByPrimaryKey(PfBorderRecommenReward record);
}