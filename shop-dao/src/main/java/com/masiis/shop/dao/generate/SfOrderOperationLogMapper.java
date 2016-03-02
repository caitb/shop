/*
 * SfOrderOperationLogMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao.generate;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface SfOrderOperationLogMapper {
    int countByExample(SfOrderOperationLogExample example);

    int deleteByExample(SfOrderOperationLogExample example);

    @Delete({
        "delete from sf_order_operation_log",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into sf_order_operation_log (id)",
        "values (#{id,jdbcType=BIGINT})"
    })
    int insert(SfOrderOperationLog record);

    int insertSelective(SfOrderOperationLog record);

    List<SfOrderOperationLog> selectByExample(SfOrderOperationLogExample example);

    int updateByExampleSelective(@Param("record") SfOrderOperationLog record, @Param("example") SfOrderOperationLogExample example);

    int updateByExample(@Param("record") SfOrderOperationLog record, @Param("example") SfOrderOperationLogExample example);
}