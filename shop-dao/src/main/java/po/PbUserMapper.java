/*
 * PbUserMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-03 Created
 */
package po;

import com.masiis.shop.dao.PbUser;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface PbUserMapper {
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

    @Select({
        "select",
        "id, user_name, true_name, password, email, sex, age, phone",
        "from pb_user",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("BaseResultMap")
    PbUser selectByPrimaryKey(Long id);

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