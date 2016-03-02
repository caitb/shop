/*
 * SfOrderConsigneeMapper.java
 * Copyright(C) 2014-2016 ÂóÊ¿¼¯ÍÅ
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao;

import com.masiis.shop.dao.SfOrderConsignee;
import com.masiis.shop.dao.SfOrderConsigneeExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface SfOrderConsigneeMapper {
    int countByExample(SfOrderConsigneeExample example);

    int deleteByExample(SfOrderConsigneeExample example);

    @Delete({
        "delete from sf_order_consignee",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into sf_order_consignee (id)",
        "values (#{id,jdbcType=BIGINT})"
    })
    int insert(SfOrderConsignee record);

    int insertSelective(SfOrderConsignee record);

    List<SfOrderConsignee> selectByExample(SfOrderConsigneeExample example);

    int updateByExampleSelective(@Param("record") SfOrderConsignee record, @Param("example") SfOrderConsigneeExample example);

    int updateByExample(@Param("record") SfOrderConsignee record, @Param("example") SfOrderConsigneeExample example);
}