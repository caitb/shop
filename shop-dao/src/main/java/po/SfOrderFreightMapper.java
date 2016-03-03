/*
 * SfOrderFreightMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-03 Created
 */
package po;

import com.masiis.shop.dao.SfOrderFreight;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;

public interface SfOrderFreightMapper {
    @Delete({
        "delete from sf_order_freight",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into sf_order_freight (id)",
        "values (#{id,jdbcType=BIGINT})"
    })
    int insert(SfOrderFreight record);

    int insertSelective(SfOrderFreight record);
}