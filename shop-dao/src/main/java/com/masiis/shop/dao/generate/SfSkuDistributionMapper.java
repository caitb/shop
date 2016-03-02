/*
 * SfSkuDistributionMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao.generate;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface SfSkuDistributionMapper {
    int countByExample(SfSkuDistributionExample example);

    int deleteByExample(SfSkuDistributionExample example);

    @Delete({
        "delete from sf_sku_distribution",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into sf_sku_distribution (id, sku_id, ",
        "sort, discount, ",
        "remark)",
        "values (#{id,jdbcType=INTEGER}, #{skuId,jdbcType=INTEGER}, ",
        "#{sort,jdbcType=INTEGER}, #{discount,jdbcType=DECIMAL}, ",
        "#{remark,jdbcType=VARCHAR})"
    })
    int insert(SfSkuDistribution record);

    int insertSelective(SfSkuDistribution record);

    List<SfSkuDistribution> selectByExample(SfSkuDistributionExample example);

    @Select({
        "select",
        "id, sku_id, sort, discount, remark",
        "from sf_sku_distribution",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    SfSkuDistribution selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SfSkuDistribution record, @Param("example") SfSkuDistributionExample example);

    int updateByExample(@Param("record") SfSkuDistribution record, @Param("example") SfSkuDistributionExample example);

    int updateByPrimaryKeySelective(SfSkuDistribution record);

    @Update({
        "update sf_sku_distribution",
        "set sku_id = #{skuId,jdbcType=INTEGER},",
          "sort = #{sort,jdbcType=INTEGER},",
          "discount = #{discount,jdbcType=DECIMAL},",
          "remark = #{remark,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(SfSkuDistribution record);
}