/*
 * SfMessageSrRelationMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-07-05 Created
 */
package com.masiis.shop.dao.mall.message;

import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.SfMessageSrRelation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SfMessageSrRelationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SfMessageSrRelation record);

    SfMessageSrRelation selectByPrimaryKey(Long id);

    List<SfMessageSrRelation> selectAll();

    int updateByPrimaryKey(SfMessageSrRelation record);

    Long countNumsFromUser(@Param("userId") Long userId);
}