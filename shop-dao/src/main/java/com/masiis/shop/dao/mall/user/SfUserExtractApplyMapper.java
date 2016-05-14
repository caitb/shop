/*
 * SfUserExtractApplyMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-04-10 Created
 */
package com.masiis.shop.dao.mall.user;

import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.SfUserExtractApply;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface SfUserExtractApplyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SfUserExtractApply record);

    SfUserExtractApply selectByPrimaryKey(Long id);

    List<SfUserExtractApply> selectAll();

    int updateByPrimaryKey(SfUserExtractApply record);

    /**
     * 根据用户id及时间查询数量
     * @param userid
     * @param start
     * @param end
     * @return
     */
    int selectCountByUserAndDate(@Param("userid") Long userid,
                                 @Param("start") Date start,
                                 @Param("end") Date end);

    /**
     * 根据用户id及时间查询list
     * @param userid
     * @param start
     * @param end
     * @return
     */
    List<SfUserExtractApply> selectListByUserAndDate(@Param("userid") Long userid,
                                                     @Param("start") Date start,
                                                     @Param("end") Date end);

    List<SfUserExtractApply> selectByMap(Map<String, Object> conditionMap);

    Map<String,BigDecimal> selectextractFeeByUserId(Long userId);
}