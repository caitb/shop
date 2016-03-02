/*
 * SfOrderFreightMapper.java
 * Copyright(C) 2014-2016 ÂóÊ¿¼¯ÍÅ
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao;

import com.masiis.shop.dao.SfOrderFreight;
import com.masiis.shop.dao.SfOrderFreightExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface SfOrderFreightMapper {
    int countByExample(SfOrderFreightExample example);

    int deleteByExample(SfOrderFreightExample example);

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

    List<SfOrderFreight> selectByExample(SfOrderFreightExample example);

    int updateByExampleSelective(@Param("record") SfOrderFreight record, @Param("example") SfOrderFreightExample example);

    int updateByExample(@Param("record") SfOrderFreight record, @Param("example") SfOrderFreightExample example);
}