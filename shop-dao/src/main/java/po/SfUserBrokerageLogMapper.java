/*
 * SfUserBrokerageLogMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-03 Created
 */
package po;

import com.masiis.shop.dao.SfUserBrokerageLog;
import org.apache.ibatis.annotations.Insert;

public interface SfUserBrokerageLogMapper {
    @Insert({
        "insert into sf_user_brokerage_log (id)",
        "values (#{id,jdbcType=BIGINT})"
    })
    int insert(SfUserBrokerageLog record);

    int insertSelective(SfUserBrokerageLog record);
}