/*
 * PfUserRecommenRelationMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-06-14 Created
 */
package com.masiis.shop.dao.platform.user;

import com.masiis.shop.dao.beans.user.UserRecommend;
import com.masiis.shop.dao.po.PfUserRecommenRelation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PfUserRecommenRelationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PfUserRecommenRelation record);

    PfUserRecommenRelation selectByPrimaryKey(Integer id);

    List<PfUserRecommenRelation> selectAll();

    int updateByPrimaryKey(PfUserRecommenRelation record);

    List<PfUserRecommenRelation> selectByCondition(PfUserRecommenRelation pfUserRecommenRelation);

    PfUserRecommenRelation selectRecommenRelationByUserIdAndSkuId(@Param("userId") Long userId,@Param("skuId") Integer skuId);

    /**
     * 帮我推荐的
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

    /**
     * 帮我推荐的人详情列表
     * @author muchaofeng
     * @date 2016/6/17 13:45
     */

    List<UserRecommend> selectGiveSum(Long  userId);

    /**
     * 帮我推荐的单人单品推荐人数
     * @author muchaofeng
     * @date 2016/6/17 13:55
     */
    int selectGiveNum(@Param("userId") Long userId,@Param("skuId") Integer skuId);

    /**
     * 帮我推荐的人id集合
     * @author muchaofeng
     * @date 2016/6/17 15:10
     */
    List<Long> selectGiveList(@Param("userId") Long userId,@Param("skuId") Integer skuId);

    /**
     * 我推荐的人详情
     * @author muchaofeng
     * @date 2016/6/15 17:40
     */
    List<UserRecommend> selectSumByUserId(Long  userId);

    /**
     * 我推荐的人详情(条件查询)
     * @author muchaofeng
     * @date 2016/6/17 10:25
     */
    List<UserRecommend> selectSumByLike(@Param("skuId") Integer skuId, @Param("userId") Long userId, @Param("agentLevelId") Integer agentLevelId);

    /**
     * 修改树形编码
     *
     * @param id       主键id
     * @param treeCode 属性编码
     * @return
     */
    int updateTreeCodeById(@Param("id") Integer id, @Param("treeCode") String treeCode);
}