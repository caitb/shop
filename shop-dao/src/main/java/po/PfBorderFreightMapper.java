/*
 * PfBorderFreightMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-03 Created
 */
package po;

import com.masiis.shop.dao.PfBorderFreight;
import org.apache.ibatis.annotations.Insert;

public interface PfBorderFreightMapper {
    @Insert({
        "insert into pf_border_freight (id, create_time, ",
        "pf_corder_id, ship_man_id, ",
        "ship_man_name, freight)",
        "values (#{id,jdbcType=BIGINT}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{pfCorderId,jdbcType=BIGINT}, #{shipManId,jdbcType=INTEGER}, ",
        "#{shipManName,jdbcType=VARCHAR}, #{freight,jdbcType=VARCHAR})"
    })
    int insert(PfBorderFreight record);

    int insertSelective(PfBorderFreight record);
}