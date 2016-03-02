/*
 * PfBorderConsigneeMapper.java
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

public interface PfBorderConsigneeMapper {
    int countByExample(PfBorderConsigneeExample example);

    int deleteByExample(PfBorderConsigneeExample example);

    @Delete({
        "delete from pf_border_consignee",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into pf_border_consignee (id, create_time, ",
        "pf_border_id, user_id, ",
        "consignee, mobile, ",
        "province_id, province_name, ",
        "city_id, city_name, ",
        "region_id, region_name, ",
        "address, remark)",
        "values (#{id,jdbcType=BIGINT}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{pfBorderId,jdbcType=BIGINT}, #{userId,jdbcType=BIGINT}, ",
        "#{consignee,jdbcType=VARCHAR}, #{mobile,jdbcType=VARCHAR}, ",
        "#{provinceId,jdbcType=INTEGER}, #{provinceName,jdbcType=VARCHAR}, ",
        "#{cityId,jdbcType=INTEGER}, #{cityName,jdbcType=VARCHAR}, ",
        "#{regionId,jdbcType=INTEGER}, #{regionName,jdbcType=VARCHAR}, ",
        "#{address,jdbcType=VARCHAR}, #{remark,jdbcType=VARCHAR})"
    })
    int insert(PfBorderConsignee record);

    int insertSelective(PfBorderConsignee record);

    List<PfBorderConsignee> selectByExample(PfBorderConsigneeExample example);

    @Select({
        "select",
        "id, create_time, pf_border_id, user_id, consignee, mobile, province_id, province_name, ",
        "city_id, city_name, region_id, region_name, address, remark",
        "from pf_border_consignee",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("BaseResultMap")
    PfBorderConsignee selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PfBorderConsignee record, @Param("example") PfBorderConsigneeExample example);

    int updateByExample(@Param("record") PfBorderConsignee record, @Param("example") PfBorderConsigneeExample example);

    int updateByPrimaryKeySelective(PfBorderConsignee record);

    @Update({
        "update pf_border_consignee",
        "set create_time = #{createTime,jdbcType=TIMESTAMP},",
          "pf_border_id = #{pfBorderId,jdbcType=BIGINT},",
          "user_id = #{userId,jdbcType=BIGINT},",
          "consignee = #{consignee,jdbcType=VARCHAR},",
          "mobile = #{mobile,jdbcType=VARCHAR},",
          "province_id = #{provinceId,jdbcType=INTEGER},",
          "province_name = #{provinceName,jdbcType=VARCHAR},",
          "city_id = #{cityId,jdbcType=INTEGER},",
          "city_name = #{cityName,jdbcType=VARCHAR},",
          "region_id = #{regionId,jdbcType=INTEGER},",
          "region_name = #{regionName,jdbcType=VARCHAR},",
          "address = #{address,jdbcType=VARCHAR},",
          "remark = #{remark,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(PfBorderConsignee record);
}