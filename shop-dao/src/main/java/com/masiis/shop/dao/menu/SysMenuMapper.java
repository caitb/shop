/*
 * SysMenuMapper.java
 * Copyright(C) 20xx-2015 xxxxxx¹«Ë¾
 * All rights reserved.
 * -----------------------------------------------
 * 2016-02-24 Created
 */
package com.masiis.shop.dao.menu;

import com.masiis.shop.dao.menu.SysMenu;
import com.masiis.shop.dao.menu.SysMenuExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface SysMenuMapper {
    int countByExample(SysMenuExample example);

    int deleteByExample(SysMenuExample example);

    @Delete({
        "delete from sys_menu",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into sys_menu (id, name, ",
        "icon, url, parent_id, ",
        "is_deleted, create_time, ",
        "update_time, rank, ",
        "remark)",
        "values (#{id,jdbcType=BIGINT}, #{name,jdbcType=VARCHAR}, ",
        "#{icon,jdbcType=VARCHAR}, #{url,jdbcType=VARCHAR}, #{parentId,jdbcType=BIGINT}, ",
        "#{isDeleted,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP}, #{rank,jdbcType=INTEGER}, ",
        "#{remark,jdbcType=VARCHAR})"
    })
    int insert(SysMenu record);

    int insertSelective(SysMenu record);

    List<SysMenu> selectByExample(SysMenuExample example);

    @Select({
        "select",
        "id, name, icon, url, parent_id, is_deleted, create_time, update_time, rank, ",
        "remark",
        "from sys_menu",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("BaseResultMap")
    SysMenu selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SysMenu record, @Param("example") SysMenuExample example);

    int updateByExample(@Param("record") SysMenu record, @Param("example") SysMenuExample example);

    int updateByPrimaryKeySelective(SysMenu record);

    @Update({
        "update sys_menu",
        "set name = #{name,jdbcType=VARCHAR},",
          "icon = #{icon,jdbcType=VARCHAR},",
          "url = #{url,jdbcType=VARCHAR},",
          "parent_id = #{parentId,jdbcType=BIGINT},",
          "is_deleted = #{isDeleted,jdbcType=INTEGER},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "rank = #{rank,jdbcType=INTEGER},",
          "remark = #{remark,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(SysMenu record);
}