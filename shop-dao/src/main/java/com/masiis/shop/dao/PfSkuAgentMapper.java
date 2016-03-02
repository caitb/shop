/*
 * PfSkuAgentMapper.java
 * Copyright(C) 2014-2016 ÂóÊ¿¼¯ÍÅ
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao;

import com.masiis.shop.dao.PfSkuAgent;
import com.masiis.shop.dao.PfSkuAgentExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface PfSkuAgentMapper {
    int countByExample(PfSkuAgentExample example);

    int deleteByExample(PfSkuAgentExample example);

    @Delete({
        "delete from pf_sku_agent",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into pf_sku_agent (id, sku_id, ",
        "agent_level_id, discount, ",
        "quantity, remark)",
        "values (#{id,jdbcType=INTEGER}, #{skuId,jdbcType=INTEGER}, ",
        "#{agentLevelId,jdbcType=INTEGER}, #{discount,jdbcType=DECIMAL}, ",
        "#{quantity,jdbcType=INTEGER}, #{remark,jdbcType=VARCHAR})"
    })
    int insert(PfSkuAgent record);

    int insertSelective(PfSkuAgent record);

    List<PfSkuAgent> selectByExample(PfSkuAgentExample example);

    @Select({
        "select",
        "id, sku_id, agent_level_id, discount, quantity, remark",
        "from pf_sku_agent",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    PfSkuAgent selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PfSkuAgent record, @Param("example") PfSkuAgentExample example);

    int updateByExample(@Param("record") PfSkuAgent record, @Param("example") PfSkuAgentExample example);

    int updateByPrimaryKeySelective(PfSkuAgent record);

    @Update({
        "update pf_sku_agent",
        "set sku_id = #{skuId,jdbcType=INTEGER},",
          "agent_level_id = #{agentLevelId,jdbcType=INTEGER},",
          "discount = #{discount,jdbcType=DECIMAL},",
          "quantity = #{quantity,jdbcType=INTEGER},",
          "remark = #{remark,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(PfSkuAgent record);
}