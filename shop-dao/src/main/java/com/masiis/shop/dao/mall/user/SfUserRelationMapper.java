/*
 * SfUserRelationMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-04-08 Created
 */
package com.masiis.shop.dao.mall.user;

import com.masiis.shop.dao.po.SfUserRelation;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
@Repository
public interface SfUserRelationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SfUserRelation record);

    SfUserRelation selectByPrimaryKey(Long id);

    List<SfUserRelation> selectAll();

    int updateByPrimaryKey(SfUserRelation record);

    SfUserRelation getSfUserRelationByUserId(Long userId);

    SfUserRelation getSfUserRelationByUserPid(Long userPid);

    List<SfUserRelation> getThreeDistributionList(Long userPid);
}