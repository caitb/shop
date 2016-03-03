/*
 * PbMenuMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-03 Created
 */
package po;

import com.masiis.shop.dao.PbMenu;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface PbMenuMapper {
    @Delete({
        "delete from pb_menu",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into pb_menu (id, name, ",
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
    int insert(PbMenu record);

    int insertSelective(PbMenu record);

    @Select({
        "select",
        "id, name, icon, url, parent_id, is_deleted, create_time, update_time, rank, ",
        "remark",
        "from pb_menu",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("BaseResultMap")
    PbMenu selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PbMenu record);

    @Update({
        "update pb_menu",
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
    int updateByPrimaryKey(PbMenu record);
}