/*
 * PfUserRecommenRelationMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-06-14 Created
 */
package com.masiis.shop.dao.platform.user;

import com.masiis.shop.dao.po.PfUserRecommenRelation;
import java.util.List;

public interface PfUserRecommenRelationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PfUserRecommenRelation record);

    PfUserRecommenRelation selectByPrimaryKey(Integer id);

    List<PfUserRecommenRelation> selectAll();

    int updateByPrimaryKey(PfUserRecommenRelation record);
}