/*
 * ComUserExtractApplyMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-17 Created
 */
package com.masiis.shop.dao.platform.user;


import com.masiis.shop.dao.po.ComUserExtractApply;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ComUserExtractApplyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ComUserExtractApply record);

    ComUserExtractApply selectByPrimaryKey(Long id);

    List<ComUserExtractApply> selectAll();

    int updateByPrimaryKey(ComUserExtractApply record);

    void pass(Long id);

    void refuse(Long id);

    void pay(Long id);

    List<ComUserExtractApply> selectByUserAndDate(@Param("userid") Long userid,
                                                  @Param("start") Date start,
                                                  @Param("end") Date end);

    /**
     * 根据用户id和时间区间查询提现记录数
     *
     * @param userid
     * @param start
     * @param end
     * @return
     */
    Integer selectNumsByUserAndDate(@Param("userid") Long userid,
                                    @Param("start") Date start,
                                    @Param("end") Date end);

    List<ComUserExtractApply> selectByUserAndDateAndPageNum(@Param("userid") Long userid,
                                                            @Param("start") Date start,
                                                            @Param("end") Date end,
                                                            @Param("startNum") Integer startNum,
                                                            @Param("qSize") Integer qSize);

    List<ComUserExtractApply> selectByCondition(Map<String, Object> con);

    Map<String, BigDecimal> selectSumExtractfeeByUserId(@Param("userId") Long userId);
}