/*
 * PfUserSkuMapper.java
 * Copyright(C) 2014-2016 ÂóÊ¿¼¯ÍÅ
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao;

import com.masiis.shop.dao.PfUserSku;
import com.masiis.shop.dao.PfUserSkuExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface PfUserSkuMapper {
    int countByExample(PfUserSkuExample example);

    int deleteByExample(PfUserSkuExample example);

    @Delete({
        "delete from pf_user_sku",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into pf_user_sku (id, create_time, ",
        "code, pid, sku_id, ",
        "agent_level_id, is_pay, ",
        "is_certificate, pf_corder_id, ",
        "is_authorized, remark)",
        "values (#{id,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{code,jdbcType=VARCHAR}, #{pid,jdbcType=INTEGER}, #{skuId,jdbcType=INTEGER}, ",
        "#{agentLevelId,jdbcType=INTEGER}, #{isPay,jdbcType=INTEGER}, ",
        "#{isCertificate,jdbcType=INTEGER}, #{pfCorderId,jdbcType=BIGINT}, ",
        "#{isAuthorized,jdbcType=INTEGER}, #{remark,jdbcType=VARCHAR})"
    })
    int insert(PfUserSku record);

    int insertSelective(PfUserSku record);

    List<PfUserSku> selectByExample(PfUserSkuExample example);

    @Select({
        "select",
        "id, create_time, code, pid, sku_id, agent_level_id, is_pay, is_certificate, ",
        "pf_corder_id, is_authorized, remark",
        "from pf_user_sku",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    PfUserSku selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PfUserSku record, @Param("example") PfUserSkuExample example);

    int updateByExample(@Param("record") PfUserSku record, @Param("example") PfUserSkuExample example);

    int updateByPrimaryKeySelective(PfUserSku record);

    @Update({
        "update pf_user_sku",
        "set create_time = #{createTime,jdbcType=TIMESTAMP},",
          "code = #{code,jdbcType=VARCHAR},",
          "pid = #{pid,jdbcType=INTEGER},",
          "sku_id = #{skuId,jdbcType=INTEGER},",
          "agent_level_id = #{agentLevelId,jdbcType=INTEGER},",
          "is_pay = #{isPay,jdbcType=INTEGER},",
          "is_certificate = #{isCertificate,jdbcType=INTEGER},",
          "pf_corder_id = #{pfCorderId,jdbcType=BIGINT},",
          "is_authorized = #{isAuthorized,jdbcType=INTEGER},",
          "remark = #{remark,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(PfUserSku record);
}