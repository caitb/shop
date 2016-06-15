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

    List<PfUserRecommenRelation> selectByCondition(PfUserRecommenRelation pfUserRecommenRelation);

    PfUserRecommenRelation selectRecommenRelationByUserIdAndSkuId(Long userId,Integer skuId);

    /**
     * 推荐我的人
     * @author muchaofeng
     * @date 2016/6/15 14:12
     */
    int selectNumByUserId(Long  userId);

    /**
     * 我推荐的人
     * @author muchaofeng
     * @date 2016/6/15 14:12
     */
    int selectNumByUserPid(Long  userId);
}