/*
 * SfUserBrokerageLogMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao.po;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface SfUserBrokerageLogMapper {
    int countByExample(SfUserBrokerageLogExample example);

    int deleteByExample(SfUserBrokerageLogExample example);

    @Insert({
        "insert into sf_user_brokerage_log (id)",
        "values (#{id,jdbcType=BIGINT})"
    })
    int insert(SfUserBrokerageLog record);

    int insertSelective(SfUserBrokerageLog record);

    List<SfUserBrokerageLog> selectByExample(SfUserBrokerageLogExample example);

    int updateByExampleSelective(@Param("record") SfUserBrokerageLog record, @Param("example") SfUserBrokerageLogExample example);

    int updateByExample(@Param("record") SfUserBrokerageLog record, @Param("example") SfUserBrokerageLogExample example);
}