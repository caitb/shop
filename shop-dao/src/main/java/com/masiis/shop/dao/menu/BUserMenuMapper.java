package com.masiis.shop.dao.menu;

import org.apache.ibatis.annotations.*;

import java.util.List;

public interface BUserMenuMapper {
    int countByExample(BUserMenuExample example);

    int deleteByExample(BUserMenuExample example);

    @Delete({
        "delete from b_user_menu",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into b_user_menu (id, user_id, ",
        "menu_id, remark)",
        "values (#{id,jdbcType=BIGINT}, #{userId,jdbcType=BIGINT}, ",
        "#{menuId,jdbcType=BIGINT}, #{remark,jdbcType=VARCHAR})"
    })
    int insert(BUserMenu record);

    int insertSelective(BUserMenu record);

    List<BUserMenu> selectByExample(BUserMenuExample example);

    @Select({
        "select",
        "id, user_id, menu_id, remark",
        "from b_user_menu",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("BaseResultMap")
    BUserMenu selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") BUserMenu record, @Param("example") BUserMenuExample example);

    int updateByExample(@Param("record") BUserMenu record, @Param("example") BUserMenuExample example);

    int updateByPrimaryKeySelective(BUserMenu record);

    @Update({
        "update b_user_menu",
        "set user_id = #{userId,jdbcType=BIGINT},",
          "menu_id = #{menuId,jdbcType=BIGINT},",
          "remark = #{remark,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(BUserMenu record);


    List<Long> findMenuIdsByUserId(Long userId);
}