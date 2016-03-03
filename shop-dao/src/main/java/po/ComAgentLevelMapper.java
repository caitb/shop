/*
 * ComAgentLevelMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-03 Created
 */
package po;

import com.masiis.shop.dao.ComAgentLevel;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface ComAgentLevelMapper {
    @Delete({
        "delete from com_agent_level",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into com_agent_level (id, create_time, ",
        "name, img_url, lower, ",
        "discount, remark)",
        "values (#{id,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{name,jdbcType=VARCHAR}, #{imgUrl,jdbcType=VARCHAR}, #{lower,jdbcType=DECIMAL}, ",
        "#{discount,jdbcType=DECIMAL}, #{remark,jdbcType=VARCHAR})"
    })
    int insert(ComAgentLevel record);

    int insertSelective(ComAgentLevel record);

    @Select({
        "select",
        "id, create_time, name, img_url, lower, discount, remark",
        "from com_agent_level",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    ComAgentLevel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ComAgentLevel record);

    @Update({
        "update com_agent_level",
        "set create_time = #{createTime,jdbcType=TIMESTAMP},",
          "name = #{name,jdbcType=VARCHAR},",
          "img_url = #{imgUrl,jdbcType=VARCHAR},",
          "lower = #{lower,jdbcType=DECIMAL},",
          "discount = #{discount,jdbcType=DECIMAL},",
          "remark = #{remark,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(ComAgentLevel record);
}