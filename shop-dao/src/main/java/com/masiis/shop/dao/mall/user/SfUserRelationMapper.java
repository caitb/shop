/*
 * SfUserRelationMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-04-08 Created
 */
package com.masiis.shop.dao.mall.user;

import com.masiis.shop.dao.po.SfUserRelation;
import java.util.List;

public interface SfUserRelationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SfUserRelation record);

    SfUserRelation selectByPrimaryKey(Long id);

    List<SfUserRelation> selectAll();

    int updateByPrimaryKey(SfUserRelation record);

    SfUserRelation getSfUserRelationByUserId(Long userId);

    SfUserRelation getSfUserRelationByUserPid(Long userPid);
}