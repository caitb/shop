/*
 * SfOrderItemMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao.generate;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface SfOrderItemMapper {
    int countByExample(SfOrderItemExample example);

    int deleteByExample(SfOrderItemExample example);

    @Insert({
        "insert into sf_order_item (id)",
        "values (#{id,jdbcType=BIGINT})"
    })
    int insert(SfOrderItem record);

    int insertSelective(SfOrderItem record);

    List<SfOrderItem> selectByExample(SfOrderItemExample example);

    int updateByExampleSelective(@Param("record") SfOrderItem record, @Param("example") SfOrderItemExample example);

    int updateByExample(@Param("record") SfOrderItem record, @Param("example") SfOrderItemExample example);
}