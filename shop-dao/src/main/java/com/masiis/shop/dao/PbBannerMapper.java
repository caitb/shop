/*
 * PbBannerMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao;

import com.masiis.shop.dao.PbBanner;
import com.masiis.shop.dao.PbBannerExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface PbBannerMapper {
    int countByExample(PbBannerExample example);

    int deleteByExample(PbBannerExample example);

    @Delete({
        "delete from pb_banner",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into pb_banner (id, create_time, ",
        "name, img_url, hyperlink_url, ",
        "remark)",
        "values (#{id,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{name,jdbcType=VARCHAR}, #{imgUrl,jdbcType=VARCHAR}, #{hyperlinkUrl,jdbcType=VARCHAR}, ",
        "#{remark,jdbcType=VARCHAR})"
    })
    int insert(PbBanner record);

    int insertSelective(PbBanner record);

    List<PbBanner> selectByExample(PbBannerExample example);

    @Select({
        "select",
        "id, create_time, name, img_url, hyperlink_url, remark",
        "from pb_banner",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    PbBanner selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PbBanner record, @Param("example") PbBannerExample example);

    int updateByExample(@Param("record") PbBanner record, @Param("example") PbBannerExample example);

    int updateByPrimaryKeySelective(PbBanner record);

    @Update({
        "update pb_banner",
        "set create_time = #{createTime,jdbcType=TIMESTAMP},",
          "name = #{name,jdbcType=VARCHAR},",
          "img_url = #{imgUrl,jdbcType=VARCHAR},",
          "hyperlink_url = #{hyperlinkUrl,jdbcType=VARCHAR},",
          "remark = #{remark,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(PbBanner record);
}