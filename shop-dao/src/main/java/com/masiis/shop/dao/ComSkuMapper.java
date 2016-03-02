/*
 * ComSkuMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao;

import com.masiis.shop.dao.ComSku;
import com.masiis.shop.dao.ComSkuExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface ComSkuMapper {
    int countByExample(ComSkuExample example);

    int deleteByExample(ComSkuExample example);

    @Delete({
        "delete from com_sku",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into com_sku (id, name, ",
        "spu_id, create_time, ",
        "create_man, modify_time, ",
        "modify_man, bar_code, ",
        "price_cost, price_market, ",
        "price_retail, remark)",
        "values (#{id,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR}, ",
        "#{spuId,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{createMan,jdbcType=BIGINT}, #{modifyTime,jdbcType=TIMESTAMP}, ",
        "#{modifyMan,jdbcType=BIGINT}, #{barCode,jdbcType=VARCHAR}, ",
        "#{priceCost,jdbcType=DECIMAL}, #{priceMarket,jdbcType=DECIMAL}, ",
        "#{priceRetail,jdbcType=DECIMAL}, #{remark,jdbcType=VARCHAR})"
    })
    int insert(ComSku record);

    int insertSelective(ComSku record);

    List<ComSku> selectByExample(ComSkuExample example);

    @Select({
        "select",
        "id, name, spu_id, create_time, create_man, modify_time, modify_man, bar_code, ",
        "price_cost, price_market, price_retail, remark",
        "from com_sku",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    ComSku selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ComSku record, @Param("example") ComSkuExample example);

    int updateByExample(@Param("record") ComSku record, @Param("example") ComSkuExample example);

    int updateByPrimaryKeySelective(ComSku record);

    @Update({
        "update com_sku",
        "set name = #{name,jdbcType=VARCHAR},",
          "spu_id = #{spuId,jdbcType=INTEGER},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "create_man = #{createMan,jdbcType=BIGINT},",
          "modify_time = #{modifyTime,jdbcType=TIMESTAMP},",
          "modify_man = #{modifyMan,jdbcType=BIGINT},",
          "bar_code = #{barCode,jdbcType=VARCHAR},",
          "price_cost = #{priceCost,jdbcType=DECIMAL},",
          "price_market = #{priceMarket,jdbcType=DECIMAL},",
          "price_retail = #{priceRetail,jdbcType=DECIMAL},",
          "remark = #{remark,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(ComSku record);
}