/*
 * ComUserAddressMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao.po;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface ComUserAddressMapper {
    int countByExample(ComUserAddressExample example);

    int deleteByExample(ComUserAddressExample example);

    @Delete({
        "delete from com_user_address",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into com_user_address (id, create_time, ",
        "user_id, name, zip, ",
        "province_id, province_name, ",
        "city_id, city_name, ",
        "region_id, region_name, ",
        "address, mobile, ",
        "is_default, remark)",
        "values (#{id,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{userId,jdbcType=BIGINT}, #{name,jdbcType=VARCHAR}, #{zip,jdbcType=VARCHAR}, ",
        "#{provinceId,jdbcType=INTEGER}, #{provinceName,jdbcType=VARCHAR}, ",
        "#{cityId,jdbcType=INTEGER}, #{cityName,jdbcType=VARCHAR}, ",
        "#{regionId,jdbcType=INTEGER}, #{regionName,jdbcType=VARCHAR}, ",
        "#{address,jdbcType=VARCHAR}, #{mobile,jdbcType=VARCHAR}, ",
        "#{isDefault,jdbcType=INTEGER}, #{remark,jdbcType=VARCHAR})"
    })
    int insert(ComUserAddress record);

    int insertSelective(ComUserAddress record);

    List<ComUserAddress> selectByExample(ComUserAddressExample example);

    @Select({
        "select",
        "id, create_time, user_id, name, zip, province_id, province_name, city_id, city_name, ",
        "region_id, region_name, address, mobile, is_default, remark",
        "from com_user_address",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    ComUserAddress selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ComUserAddress record, @Param("example") ComUserAddressExample example);

    int updateByExample(@Param("record") ComUserAddress record, @Param("example") ComUserAddressExample example);

    int updateByPrimaryKeySelective(ComUserAddress record);

    @Update({
        "update com_user_address",
        "set create_time = #{createTime,jdbcType=TIMESTAMP},",
          "user_id = #{userId,jdbcType=BIGINT},",
          "name = #{name,jdbcType=VARCHAR},",
          "zip = #{zip,jdbcType=VARCHAR},",
          "province_id = #{provinceId,jdbcType=INTEGER},",
          "province_name = #{provinceName,jdbcType=VARCHAR},",
          "city_id = #{cityId,jdbcType=INTEGER},",
          "city_name = #{cityName,jdbcType=VARCHAR},",
          "region_id = #{regionId,jdbcType=INTEGER},",
          "region_name = #{regionName,jdbcType=VARCHAR},",
          "address = #{address,jdbcType=VARCHAR},",
          "mobile = #{mobile,jdbcType=VARCHAR},",
          "is_default = #{isDefault,jdbcType=INTEGER},",
          "remark = #{remark,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(ComUserAddress record);
}