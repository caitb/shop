/*
 * SfUserBrokerageMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao;

import com.masiis.shop.dao.SfUserBrokerage;
import com.masiis.shop.dao.SfUserBrokerageExample;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface SfUserBrokerageMapper {
    int countByExample(SfUserBrokerageExample example);

    int deleteByExample(SfUserBrokerageExample example);

    @Insert({
        "insert into sf_user_brokerage (id)",
        "values (#{id,jdbcType=BIGINT})"
    })
    int insert(SfUserBrokerage record);

    int insertSelective(SfUserBrokerage record);

    List<SfUserBrokerage> selectByExample(SfUserBrokerageExample example);

    int updateByExampleSelective(@Param("record") SfUserBrokerage record, @Param("example") SfUserBrokerageExample example);

    int updateByExample(@Param("record") SfUserBrokerage record, @Param("example") SfUserBrokerageExample example);
}