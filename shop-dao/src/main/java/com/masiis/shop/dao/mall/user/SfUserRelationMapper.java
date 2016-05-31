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
     * @param treeCode
     * @param idIndex
     * @param treeLevelDiff
     * @return
     */
    int updateTreeCodes(@Param("treeCode") String treeCode, @Param("idIndex") Integer idIndex, @Param("treeLevelDiff") Integer treeLevelDiff);
}