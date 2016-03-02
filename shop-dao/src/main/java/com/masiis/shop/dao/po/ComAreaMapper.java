/*
 * ComAreaMapper.java
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

public interface ComAreaMapper {
    int countByExample(ComAreaExample example);

    int deleteByExample(ComAreaExample example);

    @Delete({
        "delete from com_area",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into com_area (id, create_time, ",
        "create_man, code, ",
        "name, pid, level, ",
        "name_en, shortname_en, ",
        "sort, remark)",
        "values (#{id,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{createMan,jdbcType=BIGINT}, #{code,jdbcType=VARCHAR}, ",
        "#{name,jdbcType=VARCHAR}, #{pid,jdbcType=INTEGER}, #{level,jdbcType=INTEGER}, ",
        "#{nameEn,jdbcType=VARCHAR}, #{shortnameEn,jdbcType=VARCHAR}, ",
        "#{sort,jdbcType=INTEGER}, #{remark,jdbcType=VARCHAR})"
    })
    int insert(ComArea record);

    int insertSelective(ComArea record);

    List<ComArea> selectByExample(ComAreaExample example);

    @Select({
        "select",
        "id, create_time, create_man, code, name, pid, level, name_en, shortname_en, ",
        "sort, remark",
        "from com_area",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    ComArea selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ComArea record, @Param("example") ComAreaExample example);

    int updateByExample(@Param("record") ComArea record, @Param("example") ComAreaExample example);

    int updateByPrimaryKeySelective(ComArea record);

    @Update({
        "update com_area",
        "set create_time = #{createTime,jdbcType=TIMESTAMP},",
          "create_man = #{createMan,jdbcType=BIGINT},",
          "code = #{code,jdbcType=VARCHAR},",
          "name = #{name,jdbcType=VARCHAR},",
          "pid = #{pid,jdbcType=INTEGER},",
          "level = #{level,jdbcType=INTEGER},",
          "name_en = #{nameEn,jdbcType=VARCHAR},",
          "shortname_en = #{shortnameEn,jdbcType=VARCHAR},",
          "sort = #{sort,jdbcType=INTEGER},",
          "remark = #{remark,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(ComArea record);
}