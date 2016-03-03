/*
 * PbUserMenuMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-03 Created
 */
package po;

import com.masiis.shop.dao.PbUserMenu;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface PbUserMenuMapper {
    @Delete({
        "delete from pb_user_menu",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into pb_user_menu (id, pb_user_id, ",
        "pb_menu_id, create_time, ",
        "update_time)",
        "values (#{id,jdbcType=BIGINT}, #{pbUserId,jdbcType=BIGINT}, ",
        "#{pbMenuId,jdbcType=BIGINT}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP})"
    })
    int insert(PbUserMenu record);

    int insertSelective(PbUserMenu record);

    @Select({
        "select",
        "id, pb_user_id, pb_menu_id, create_time, update_time",
        "from pb_user_menu",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("BaseResultMap")
    PbUserMenu selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PbUserMenu record);

    @Update({
        "update pb_user_menu",
        "set pb_user_id = #{pbUserId,jdbcType=BIGINT},",
          "pb_menu_id = #{pbMenuId,jdbcType=BIGINT},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(PbUserMenu record);
}