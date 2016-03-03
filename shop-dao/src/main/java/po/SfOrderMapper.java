/*
 * SfOrderMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-03 Created
 */
package po;

import com.masiis.shop.dao.SfOrder;
import org.apache.ibatis.annotations.Insert;

public interface SfOrderMapper {
    @Insert({
        "insert into sf_order (id)",
        "values (#{id,jdbcType=BIGINT})"
    })
    int insert(SfOrder record);

    int insertSelective(SfOrder record);
}