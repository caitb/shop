/*
 * PfUserTrialMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao;

import com.masiis.shop.dao.PfUserTrial;
import com.masiis.shop.dao.PfUserTrialExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface PfUserTrialMapper {
    int countByExample(PfUserTrialExample example);

    int deleteByExample(PfUserTrialExample example);

    @Delete({
        "delete from pf_user_trial",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into pf_user_trial (id, create_time, ",
        "user_id, spu_id, sku_id, ",
        "status, reason, ",
        "name, mobile, weixin_id, ",
        "remark)",
        "values (#{id,jdbcType=BIGINT}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{userId,jdbcType=BIGINT}, #{spuId,jdbcType=INTEGER}, #{skuId,jdbcType=INTEGER}, ",
        "#{status,jdbcType=INTEGER}, #{reason,jdbcType=VARCHAR}, ",
        "#{name,jdbcType=VARCHAR}, #{mobile,jdbcType=VARCHAR}, #{weixinId,jdbcType=VARCHAR}, ",
        "#{remark,jdbcType=VARCHAR})"
    })
    int insert(PfUserTrial record);

    int insertSelective(PfUserTrial record);

    List<PfUserTrial> selectByExample(PfUserTrialExample example);

    @Select({
        "select",
        "id, create_time, user_id, spu_id, sku_id, status, reason, name, mobile, weixin_id, ",
        "remark",
        "from pf_user_trial",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("BaseResultMap")
    PfUserTrial selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PfUserTrial record, @Param("example") PfUserTrialExample example);

    int updateByExample(@Param("record") PfUserTrial record, @Param("example") PfUserTrialExample example);

    int updateByPrimaryKeySelective(PfUserTrial record);

    @Update({
        "update pf_user_trial",
        "set create_time = #{createTime,jdbcType=TIMESTAMP},",
          "user_id = #{userId,jdbcType=BIGINT},",
          "spu_id = #{spuId,jdbcType=INTEGER},",
          "sku_id = #{skuId,jdbcType=INTEGER},",
          "status = #{status,jdbcType=INTEGER},",
          "reason = #{reason,jdbcType=VARCHAR},",
          "name = #{name,jdbcType=VARCHAR},",
          "mobile = #{mobile,jdbcType=VARCHAR},",
          "weixin_id = #{weixinId,jdbcType=VARCHAR},",
          "remark = #{remark,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(PfUserTrial record);
}