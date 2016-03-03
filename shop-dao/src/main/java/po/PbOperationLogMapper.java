/*
 * PbOperationLogMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-03 Created
 */
package po;

import com.masiis.shop.dao.PbOperationLog;
import org.apache.ibatis.annotations.Insert;

public interface PbOperationLogMapper {
    @Insert({
        "insert into pb_operation_log (id)",
        "values (#{id,jdbcType=BIGINT})"
    })
    int insert(PbOperationLog record);

    int insertSelective(PbOperationLog record);
}