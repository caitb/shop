/*
 * SfUserRelationMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-03 Created
 */
package po;

import com.masiis.shop.dao.SfUserRelation;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface SfUserRelationMapper {
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

    @Select({
        "select",
        "id, create_time, user_id, parent_user_id, remark",
        "from sf_user_relation",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("BaseResultMap")
    SfUserRelation selectByPrimaryKey(Long id);

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