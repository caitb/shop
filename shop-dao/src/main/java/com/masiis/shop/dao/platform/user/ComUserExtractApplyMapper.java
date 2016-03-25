/*
 * ComUserExtractApplyMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-17 Created
 */
package com.masiis.shop.dao.platform.user;


import com.masiis.shop.dao.beans.extract.ExtractApply;
import com.masiis.shop.dao.po.ComUserExtractApply;
import com.sun.tracing.dtrace.ProviderAttributes;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Repository
public interface ComUserExtractApplyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ComUserExtractApply record);

    ComUserExtractApply selectByPrimaryKey(Long id);

    List<ComUserExtractApply> selectAll();

    int updateByPrimaryKey(ComUserExtractApply record);

    List<ExtractApply> getExtractApplyList();

    ExtractApply findById(Long id);

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
}