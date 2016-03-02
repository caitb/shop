/*
 * PbUserMapper.java
 * Copyright(C) 2014-2016 ÂóÊ¿¼¯ÍÅ
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao;

import com.masiis.shop.dao.PbUser;
import com.masiis.shop.dao.PbUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface PbUserMapper {
    int countByExample(PbUserExample example);

    int deleteByExample(PbUserExample example);

    @Delete({
        "delete from pb_user",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into pb_user (id, user_name, ",
        "true_name, password, ",
        "email, sex, age, ",
        "phone)",
        "values (#{id,jdbcType=BIGINT}, #{userName,jdbcType=VARCHAR}, ",
        "#{trueName,jdbcType=VARCHAR}, #{password,jdbcType=VARCHAR}, ",
        "#{email,jdbcType=VARCHAR}, #{sex,jdbcType=CHAR}, #{age,jdbcType=INTEGER}, ",
        "#{phone,jdbcType=VARCHAR})"
    })
    int insert(PbUser record);

    int insertSelective(PbUser record);

    List<PbUser> selectByExample(PbUserExample example);

    @Select({
        "select",
        "id, user_name, true_name, password, email, sex, age, phone",
        "from pb_user",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("BaseResultMap")
    PbUser selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PbUser record, @Param("example") PbUserExample example);

    int updateByExample(@Param("record") PbUser record, @Param("example") PbUserExample example);

    int updateByPrimaryKeySelective(PbUser record);

    @Update({
        "update pb_user",
        "set user_name = #{userName,jdbcType=VARCHAR},",
          "true_name = #{trueName,jdbcType=VARCHAR},",
          "password = #{password,jdbcType=VARCHAR},",
          "email = #{email,jdbcType=VARCHAR},",
          "sex = #{sex,jdbcType=CHAR},",
          "age = #{age,jdbcType=INTEGER},",
          "phone = #{phone,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(PbUser record);
}