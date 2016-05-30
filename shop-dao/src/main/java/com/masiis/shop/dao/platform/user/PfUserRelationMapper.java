/*
 * PfUserRelationMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-04-18 Created
 */
package com.masiis.shop.dao.platform.user;

import com.masiis.shop.dao.po.PfUserRelation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PfUserRelationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PfUserRelation record);

    PfUserRelation selectByPrimaryKey(Long id);

    List<PfUserRelation> selectAll();

    int updateByPrimaryKey(PfUserRelation record);

    PfUserRelation selectEnableByUserId(@Param("userId") Long userId, @Param("skuId") Integer skuId);

    /**
     * 根据用户id查找最新的一条记录
     * @param userId
     * @return
     */
    PfUserRelation selectLastRecordByUserId(Long userId);

    /**
     * 根据userid skuid pUserid来查询临时代理关系
     *
     * @param userId
     * @param skuId
     * @param pUserId
     * @return
     */
    PfUserRelation selectRelationByUserIdAndSkuIdAndPUserId(@Param("userId") Long userId,
                                                            @Param("skuId") Integer skuId,
                                                            @Param("pUserId") Long pUserId);

    /**
     * 根据userId和skuId修改为不可用
     *
     * @param userId
     * @param skuId
     * @return
     */
    int updateAllToUnableByUserIdAndSkuId(@Param("userId") Long userId,
                                          @Param("skuId") Integer skuId);
}