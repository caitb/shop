/*
 * ComBrandMapper.java
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

public interface ComBrandMapper {
    int countByExample(ComBrandExample example);

    int deleteByExample(ComBrandExample example);

    @Delete({
        "delete from com_brand",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into com_brand (id, create_time, ",
        "cname, ename, logo_url, ",
        "remark)",
        "values (#{id,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{cname,jdbcType=VARCHAR}, #{ename,jdbcType=VARCHAR}, #{logoUrl,jdbcType=VARCHAR}, ",
        "#{remark,jdbcType=VARCHAR})"
    })
    int insert(ComBrand record);

    int insertSelective(ComBrand record);

    List<ComBrand> selectByExample(ComBrandExample example);

    @Select({
        "select",
        "id, create_time, cname, ename, logo_url, remark",
        "from com_brand",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    ComBrand selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ComBrand record, @Param("example") ComBrandExample example);

    int updateByExample(@Param("record") ComBrand record, @Param("example") ComBrandExample example);

    int updateByPrimaryKeySelective(ComBrand record);

    @Update({
        "update com_brand",
        "set create_time = #{createTime,jdbcType=TIMESTAMP},",
          "cname = #{cname,jdbcType=VARCHAR},",
          "ename = #{ename,jdbcType=VARCHAR},",
          "logo_url = #{logoUrl,jdbcType=VARCHAR},",
          "remark = #{remark,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(ComBrand record);
}