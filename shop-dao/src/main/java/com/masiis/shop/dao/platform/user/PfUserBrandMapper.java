/*
 * PfUserBrandMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-08-04 Created
 */
package com.masiis.shop.dao.platform.user;

import java.util.List;

import com.masiis.shop.dao.po.ComBrand;
import com.masiis.shop.dao.po.PfUserBrand;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PfUserBrandMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PfUserBrand record);

    PfUserBrand selectByPrimaryKey(Integer id);

    List<PfUserBrand> selectAll();

    int updateByPrimaryKey(PfUserBrand record);

    List<PfUserBrand> selectByUserId(Long userId);

    List<PfUserBrand> selectBtBrandId(Integer brandId);

    List<ComBrand> selectBrandByUserId(@Param("userId") Long userId);

    PfUserBrand selectByUserIdAndBrandId(@Param("userId") Long userId, @Param("brandId") Integer brandId);

    Integer selectMyBrandNum(@Param("userId") Long userId);

    List<Integer> selectAgentBrandIds(Long userId);
}