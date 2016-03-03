/*
 * SfUserBrokerageMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-03 Created
 */
package po;

import com.masiis.shop.dao.SfUserBrokerage;
import org.apache.ibatis.annotations.Insert;

public interface SfUserBrokerageMapper {
    @Insert({
        "insert into sf_user_brokerage (id)",
        "values (#{id,jdbcType=BIGINT})"
    })
    int insert(SfUserBrokerage record);

    int insertSelective(SfUserBrokerage record);
}