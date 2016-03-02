/*
 * ComDictionaryMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao;

import com.masiis.shop.dao.ComDictionary;
import com.masiis.shop.dao.ComDictionaryExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface ComDictionaryMapper {
    int countByExample(ComDictionaryExample example);

    int deleteByExample(ComDictionaryExample example);

    @Delete({
        "delete from com_dictionary",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into com_dictionary (id, code, ",
        "key, value, remark)",
        "values (#{id,jdbcType=INTEGER}, #{code,jdbcType=VARCHAR}, ",
        "#{key,jdbcType=INTEGER}, #{value,jdbcType=VARCHAR}, #{remark,jdbcType=VARCHAR})"
    })
    int insert(ComDictionary record);

    int insertSelective(ComDictionary record);

    List<ComDictionary> selectByExample(ComDictionaryExample example);

    @Select({
        "select",
        "id, code, key, value, remark",
        "from com_dictionary",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    ComDictionary selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ComDictionary record, @Param("example") ComDictionaryExample example);

    int updateByExample(@Param("record") ComDictionary record, @Param("example") ComDictionaryExample example);

    int updateByPrimaryKeySelective(ComDictionary record);

    @Update({
        "update com_dictionary",
        "set code = #{code,jdbcType=VARCHAR},",
          "key = #{key,jdbcType=INTEGER},",
          "value = #{value,jdbcType=VARCHAR},",
          "remark = #{remark,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(ComDictionary record);
}