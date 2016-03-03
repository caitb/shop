/*
 * SfOrderOperationLogMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-03 Created
 */
package po;

import com.masiis.shop.dao.SfOrderOperationLog;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;

public interface SfOrderOperationLogMapper {
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
}