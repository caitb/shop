/*
 * SfShopCartMapper.java
 * Copyright(C) 2014-2016 ÂóÊ¿¼¯ÍÅ
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao;

import com.masiis.shop.dao.SfShopCart;
import com.masiis.shop.dao.SfShopCartExample;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface SfShopCartMapper {
    int countByExample(SfShopCartExample example);

    int deleteByExample(SfShopCartExample example);

    @Insert({
        "insert into sf_shop_cart (id)",
        "values (#{id,jdbcType=BIGINT})"
    })
    int insert(SfShopCart record);

    int insertSelective(SfShopCart record);

    List<SfShopCart> selectByExample(SfShopCartExample example);

    int updateByExampleSelective(@Param("record") SfShopCart record, @Param("example") SfShopCartExample example);

    int updateByExample(@Param("record") SfShopCart record, @Param("example") SfShopCartExample example);
}