package com.masiis.shop.dao.menu;

import org.apache.ibatis.annotations.*;

import java.util.List;

public interface BMenuMapper {

    int countByExample(BMenuExample example);

    int deleteByExample(BMenuExample example);

    @Delete({
        "delete from b_menu",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into b_menu (id, name, ",
        "icon, url, parent_id, ",
        "is_deleted, create_time, ",
        "update_time, rank, ",
        "remark)",
        "values (#{id,jdbcType=BIGINT}, #{name,jdbcType=VARCHAR}, ",
        "#{icon,jdbcType=VARCHAR}, #{url,jdbcType=VARCHAR}, #{parentId,jdbcType=INTEGER}, ",
        "#{isDeleted,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP}, #{rank,jdbcType=INTEGER}, ",
        "#{remark,jdbcType=VARCHAR})"
    })
    int insert(BMenu record);

    int insertSelective(BMenu record);

    List<BMenu> selectByExample(BMenuExample example);

    @Select({
        "select",
        "id, name, icon, url, parent_id, is_deleted, create_time, update_time, rank, ",
        "remark",
        "from b_menu",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("BaseResultMap")
    BMenu selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") BMenu record, @Param("example") BMenuExample example);

    int updateByExample(@Param("record") BMenu record, @Param("example") BMenuExample example);

    int updateByPrimaryKeySelective(BMenu record);

    @Update({
        "update b_menu",
        "set name = #{name,jdbcType=VARCHAR},",
          "icon = #{icon,jdbcType=VARCHAR},",
          "url = #{url,jdbcType=VARCHAR},",
          "parent_id = #{parentId,jdbcType=INTEGER},",
          "is_deleted = #{isDeleted,jdbcType=INTEGER},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "rank = #{rank,jdbcType=INTEGER},",
          "remark = #{remark,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(BMenu record);
}