/*
 * SfUserRelationMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-04-08 Created
 */
package com.masiis.shop.dao.mall.user;

import com.masiis.shop.dao.po.SfUserRelation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SfUserRelationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SfUserRelation record);

    SfUserRelation selectByPrimaryKey(Long id);

    List<SfUserRelation> selectAll();

    int updateByPrimaryKey(SfUserRelation record);

    List<SfUserRelation> getSfUserRelationByUserId(Long userId);

    List<SfUserRelation> getThreeDistributionList(Long userPid);

    SfUserRelation selectSfUserRelationByUserIdAndShopId(@Param("userId") Long userId,
                                                         @Param("shopId") Long shopId);

    /**
     * 通过treecode获取粉丝数量
     *
     * @param treeCode treeCode
     * @return Integer
     */
    Map<String, Integer> selectFansNum(@Param("treeCode") String treeCode);

    /**
     * 修改树形编码
     *
     * @param id       主键id
     * @param treeCode 属性编码
     * @return
     */
    int updateTreeCodeById(@Param("id") Long id, @Param("treeCode") String treeCode);

    /**
     * 批量修改树形编码
     *
     * @param treeCode
     * @param idIndex
     * @param treeLevelDiff
     * @return
     */
    int updateTreeCodes(@Param("treeCode") String treeCode, @Param("idIndex") Integer idIndex, @Param("treeLevelDiff") Integer treeLevelDiff);

    SfUserRelation getSfUserRelationByUserIdAndShopId(@Param("userId") Long userId, @Param("shopId") Long shopId);
}