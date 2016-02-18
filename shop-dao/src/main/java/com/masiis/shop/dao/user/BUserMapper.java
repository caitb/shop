package com.masiis.shop.dao.user;

import org.apache.ibatis.annotations.*;

import java.util.List;

public interface BUserMapper {
    int countByExample(BUserExample example);

    int deleteByExample(BUserExample example);

    @Delete({
        "delete from b_user",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into b_user (id, user_name, ",
        "true_name, password, ",
        "email, sex, age, ",
        "phone)",
        "values (#{id,jdbcType=BIGINT}, #{userName,jdbcType=VARCHAR}, ",
        "#{trueName,jdbcType=VARCHAR}, #{password,jdbcType=VARCHAR}, ",
        "#{email,jdbcType=VARCHAR}, #{sex,jdbcType=CHAR}, #{age,jdbcType=INTEGER}, ",
        "#{phone,jdbcType=VARCHAR})"
    })
    int insert(BUser record);

    int insertSelective(BUser record);

    List<BUser> selectByExample(BUserExample example);

    @Select({
        "select",
        "id, user_name, true_name, password, email, sex, age, phone",
        "from b_user",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("BaseResultMap")
    BUser selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") BUser record, @Param("example") BUserExample example);

    int updateByExample(@Param("record") BUser record, @Param("example") BUserExample example);

    int updateByPrimaryKeySelective(BUser record);

    @Update({
        "update b_user",
        "set user_name = #{userName,jdbcType=VARCHAR},",
          "true_name = #{trueName,jdbcType=VARCHAR},",
          "password = #{password,jdbcType=VARCHAR},",
          "email = #{email,jdbcType=VARCHAR},",
          "sex = #{sex,jdbcType=CHAR},",
          "age = #{age,jdbcType=INTEGER},",
          "phone = #{phone,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(BUser record);
}