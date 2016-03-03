/*
 * ComCategoryMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-03 Created
 */
package po;

import com.masiis.shop.dao.ComCategory;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface ComCategoryMapper {
    @Delete({
        "delete from com_category",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into com_category (id, create_time, ",
        "create_man, modify_time, ",
        "modify_man, name, ",
        "level, pid, sort, ",
        "is_validate, remark)",
        "values (#{id,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{createMan,jdbcType=BIGINT}, #{modifyTime,jdbcType=TIMESTAMP}, ",
        "#{modifyMan,jdbcType=BIGINT}, #{name,jdbcType=VARCHAR}, ",
        "#{level,jdbcType=TINYINT}, #{pid,jdbcType=INTEGER}, #{sort,jdbcType=INTEGER}, ",
        "#{isValidate,jdbcType=INTEGER}, #{remark,jdbcType=VARCHAR})"
    })
    int insert(ComCategory record);

    int insertSelective(ComCategory record);

    @Select({
        "select",
        "id, create_time, create_man, modify_time, modify_man, name, level, pid, sort, ",
        "is_validate, remark",
        "from com_category",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    ComCategory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ComCategory record);

    @Update({
        "update com_category",
        "set create_time = #{createTime,jdbcType=TIMESTAMP},",
          "create_man = #{createMan,jdbcType=BIGINT},",
          "modify_time = #{modifyTime,jdbcType=TIMESTAMP},",
          "modify_man = #{modifyMan,jdbcType=BIGINT},",
          "name = #{name,jdbcType=VARCHAR},",
          "level = #{level,jdbcType=TINYINT},",
          "pid = #{pid,jdbcType=INTEGER},",
          "sort = #{sort,jdbcType=INTEGER},",
          "is_validate = #{isValidate,jdbcType=INTEGER},",
          "remark = #{remark,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(ComCategory record);
}