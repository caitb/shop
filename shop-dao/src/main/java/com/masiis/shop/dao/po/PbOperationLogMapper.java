/*
 * PbOperationLogMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao.po;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface PbOperationLogMapper {
    int countByExample(PbOperationLogExample example);

    int deleteByExample(PbOperationLogExample example);

    @Insert({
        "insert into pb_operation_log (id)",
        "values (#{id,jdbcType=BIGINT})"
    })
    int insert(PbOperationLog record);

    int insertSelective(PbOperationLog record);

    List<PbOperationLog> selectByExample(PbOperationLogExample example);

    int updateByExampleSelective(@Param("record") PbOperationLog record, @Param("example") PbOperationLogExample example);

    int updateByExample(@Param("record") PbOperationLog record, @Param("example") PbOperationLogExample example);
}