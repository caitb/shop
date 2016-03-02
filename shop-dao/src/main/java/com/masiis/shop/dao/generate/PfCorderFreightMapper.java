/*
 * PfCorderFreightMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao.generate;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface PfCorderFreightMapper {
    int countByExample(PfCorderFreightExample example);

    int deleteByExample(PfCorderFreightExample example);

    @Insert({
        "insert into pf_corder_freight (id, create_time, ",
        "pf_corder_id, ship_man_id, ",
        "ship_man_name, freight)",
        "values (#{id,jdbcType=BIGINT}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{pfCorderId,jdbcType=BIGINT}, #{shipManId,jdbcType=INTEGER}, ",
        "#{shipManName,jdbcType=VARCHAR}, #{freight,jdbcType=VARCHAR})"
    })
    int insert(PfCorderFreight record);

    int insertSelective(PfCorderFreight record);

    List<PfCorderFreight> selectByExample(PfCorderFreightExample example);

    int updateByExampleSelective(@Param("record") PfCorderFreight record, @Param("example") PfCorderFreightExample example);

    int updateByExample(@Param("record") PfCorderFreight record, @Param("example") PfCorderFreightExample example);
}