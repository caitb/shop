/*
 * SfOrderMapper.java
 * Copyright(C) 2014-2016 ÂóÊ¿¼¯ÍÅ
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao;

import com.masiis.shop.dao.SfOrder;
import com.masiis.shop.dao.SfOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface SfOrderMapper {
    int countByExample(SfOrderExample example);

    int deleteByExample(SfOrderExample example);

    @Insert({
        "insert into sf_order (id)",
        "values (#{id,jdbcType=BIGINT})"
    })
    int insert(SfOrder record);

    int insertSelective(SfOrder record);

    List<SfOrder> selectByExample(SfOrderExample example);

    int updateByExampleSelective(@Param("record") SfOrder record, @Param("example") SfOrderExample example);

    int updateByExample(@Param("record") SfOrder record, @Param("example") SfOrderExample example);
}