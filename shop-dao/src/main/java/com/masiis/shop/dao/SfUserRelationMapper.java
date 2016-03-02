/*
 * SfUserRelationMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao;

import com.masiis.shop.dao.SfUserRelation;
import com.masiis.shop.dao.SfUserRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface SfUserRelationMapper {
    int countByExample(SfUserRelationExample example);

    int deleteByExample(SfUserRelationExample example);

    @Delete({
        "delete from sf_user_relation",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into sf_user_relation (id, create_time, ",
        "user_id, parent_user_id, ",
        "remark)",
        "values (#{id,jdbcType=BIGINT}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{userId,jdbcType=BIGINT}, #{parentUserId,jdbcType=BIGINT}, ",
        "#{remark,jdbcType=VARCHAR})"
    })
    int insert(SfUserRelation record);

    int insertSelective(SfUserRelation record);

    List<SfUserRelation> selectByExample(SfUserRelationExample example);

    @Select({
        "select",
        "id, create_time, user_id, parent_user_id, remark",
        "from sf_user_relation",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("BaseResultMap")
    SfUserRelation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SfUserRelation record, @Param("example") SfUserRelationExample example);

    int updateByExample(@Param("record") SfUserRelation record, @Param("example") SfUserRelationExample example);

    int updateByPrimaryKeySelective(SfUserRelation record);

    @Update({
        "update sf_user_relation",
        "set create_time = #{createTime,jdbcType=TIMESTAMP},",
          "user_id = #{userId,jdbcType=BIGINT},",
          "parent_user_id = #{parentUserId,jdbcType=BIGINT},",
          "remark = #{remark,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(SfUserRelation record);
}