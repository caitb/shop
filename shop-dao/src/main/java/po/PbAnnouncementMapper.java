/*
 * PbAnnouncementMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-03 Created
 */
package po;

import com.masiis.shop.dao.PbAnnouncement;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface PbAnnouncementMapper {
    @Delete({
        "delete from pb_announcement",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into pb_announcement (id, create_time, ",
        "name, hyperlink_url, ",
        "remark)",
        "values (#{id,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{name,jdbcType=VARCHAR}, #{hyperlinkUrl,jdbcType=VARCHAR}, ",
        "#{remark,jdbcType=VARCHAR})"
    })
    int insert(PbAnnouncement record);

    int insertSelective(PbAnnouncement record);

    @Select({
        "select",
        "id, create_time, name, hyperlink_url, remark",
        "from pb_announcement",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    PbAnnouncement selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PbAnnouncement record);

    @Update({
        "update pb_announcement",
        "set create_time = #{createTime,jdbcType=TIMESTAMP},",
          "name = #{name,jdbcType=VARCHAR},",
          "hyperlink_url = #{hyperlinkUrl,jdbcType=VARCHAR},",
          "remark = #{remark,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(PbAnnouncement record);
}