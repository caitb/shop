/*
 * PbAnnouncementMapper.java
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

public interface PbAnnouncementMapper {
    int countByExample(PbAnnouncementExample example);

    int deleteByExample(PbAnnouncementExample example);

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

    List<PbAnnouncement> selectByExample(PbAnnouncementExample example);

    @Select({
        "select",
        "id, create_time, name, hyperlink_url, remark",
        "from pb_announcement",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    PbAnnouncement selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PbAnnouncement record, @Param("example") PbAnnouncementExample example);

    int updateByExample(@Param("record") PbAnnouncement record, @Param("example") PbAnnouncementExample example);

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