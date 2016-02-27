/*
 * SysUserMenuMapper.java
 * Copyright(C) 20xx-2015 xxxxxx��˾
 * All rights reserved.
 * -----------------------------------------------
 * 2016-02-24 Created
 */
package com.masiis.shop.dao.usermenu;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface SysUserMenuMapper {
    int countByExample(SysUserMenuExample example);

    int deleteByExample(SysUserMenuExample example);

    @Delete({
        "delete from sys_user_menu",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into sys_user_menu (id, user_id, ",
        "menu_id, remark)",
        "values (#{id,jdbcType=BIGINT}, #{userId,jdbcType=BIGINT}, ",
        "#{menuId,jdbcType=BIGINT}, #{remark,jdbcType=VARCHAR})"
    })
    int insert(SysUserMenu record);

    int insertSelective(SysUserMenu record);

    List<SysUserMenu> selectByExample(SysUserMenuExample example);

    @Select({
        "select",
        "id, user_id, menu_id, remark",
        "from sys_user_menu",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("BaseResultMap")
    SysUserMenu selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SysUserMenu record, @Param("example") SysUserMenuExample example);

    int updateByExample(@Param("record") SysUserMenu record, @Param("example") SysUserMenuExample example);

    int updateByPrimaryKeySelective(SysUserMenu record);

    @Update({
        "update sys_user_menu",
        "set user_id = #{userId,jdbcType=BIGINT},",
          "menu_id = #{menuId,jdbcType=BIGINT},",
          "remark = #{remark,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(SysUserMenu record);
}