/*
 * SysUserMapper.java
 * Copyright(C) 20xx-2015 xxxxxx��˾
 * All rights reserved.
 * -----------------------------------------------
 * 2016-02-24 Created
 */
package com.masiis.shop.dao.user;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface SysUserMapper {
    int countByExample(SysUserExample example);

    int deleteByExample(SysUserExample example);

    @Delete({
        "delete from sys_user",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into sys_user (id, user_name, ",
        "true_name, password, ",
        "email, sex, age, ",
        "phone)",
        "values (#{id,jdbcType=BIGINT}, #{userName,jdbcType=VARCHAR}, ",
        "#{trueName,jdbcType=VARCHAR}, #{password,jdbcType=VARCHAR}, ",
        "#{email,jdbcType=VARCHAR}, #{sex,jdbcType=CHAR}, #{age,jdbcType=INTEGER}, ",
        "#{phone,jdbcType=VARCHAR})"
    })
    int insert(SysUser record);

    int insertSelective(SysUser record);

    List<SysUser> selectByExample(SysUserExample example);

    @Select({
        "select",
        "id, user_name, true_name, password, email, sex, age, phone",
        "from sys_user",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("BaseResultMap")
    SysUser selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SysUser record, @Param("example") SysUserExample example);

    int updateByExample(@Param("record") SysUser record, @Param("example") SysUserExample example);

    int updateByPrimaryKeySelective(SysUser record);

    @Update({
        "update sys_user",
        "set user_name = #{userName,jdbcType=VARCHAR},",
          "true_name = #{trueName,jdbcType=VARCHAR},",
          "password = #{password,jdbcType=VARCHAR},",
          "email = #{email,jdbcType=VARCHAR},",
          "sex = #{sex,jdbcType=CHAR},",
          "age = #{age,jdbcType=INTEGER},",
          "phone = #{phone,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(SysUser record);
}