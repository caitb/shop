/*
 * PfUserSkuMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-03 Created
 */
package com.masiis.shop.dao.platform.user;

import com.masiis.shop.dao.beans.user.CountGroup;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;


@Repository
public interface CountGroupMapper {
    /**
     * 统计团队人数、销售额、订单总数
     *
     * @author muchaofeng
     * @date 2016/6/8 18:14
     */
    CountGroup countGroup(@Param("treeCode") String treeCode);


    /**
     * 统计推荐团队人数、销售额、订单总数
     *
     * @param treeCode pf_user_sku 表 tree_code
     * @return
     */
    CountGroup countRecommendGroup(@Param("treeCode") String treeCode);

    /**
     * 统计所有品牌的团队人数和总销售额
     *
     * @param userId
     * @return
     */
    Map<String, Object> allCountGroup(@Param("userId") Long userId);

    /**
     * 根据brandId统计团队人数和总销售额
     *
     * @param userId
     * @param brandId
     * @return
     */
    Map<String, Object> countGroupByBrandId(@Param("userId") Long userId, @Param("brandId") Integer brandId);
}