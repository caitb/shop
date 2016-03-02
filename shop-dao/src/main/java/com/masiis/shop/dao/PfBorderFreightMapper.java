/*
 * PfBorderFreightMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao;

import com.masiis.shop.dao.PfBorderFreight;
import com.masiis.shop.dao.PfBorderFreightExample;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface PfBorderFreightMapper {
    int countByExample(PfBorderFreightExample example);

    int deleteByExample(PfBorderFreightExample example);

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

    List<PfBorderFreight> selectByExample(PfBorderFreightExample example);

    int updateByExampleSelective(@Param("record") PfBorderFreight record, @Param("example") PfBorderFreightExample example);

    int updateByExample(@Param("record") PfBorderFreight record, @Param("example") PfBorderFreightExample example);
}