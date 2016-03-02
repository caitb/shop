/*
 * PfUserCertificateMapper.java
 * Copyright(C) 2014-2016 ÂóÊ¿¼¯ÍÅ
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao;

import com.masiis.shop.dao.PfUserCertificate;
import com.masiis.shop.dao.PfUserCertificateExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface PfUserCertificateMapper {
    int countByExample(PfUserCertificateExample example);

    int deleteByExample(PfUserCertificateExample example);

    @Delete({
        "delete from pf_user_certificate",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into pf_user_certificate (id, create_time, ",
        "code, pf_user_sku_id, ",
        "user_id, spu_id, sku_id, ",
        "id_card, mobile, ",
        "wx_id, begin_time, ",
        "end_time, agent_level_id, ",
        "img_url, status, ",
        "remark)",
        "values (#{id,jdbcType=BIGINT}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{code,jdbcType=VARCHAR}, #{pfUserSkuId,jdbcType=INTEGER}, ",
        "#{userId,jdbcType=BIGINT}, #{spuId,jdbcType=INTEGER}, #{skuId,jdbcType=INTEGER}, ",
        "#{idCard,jdbcType=VARCHAR}, #{mobile,jdbcType=VARCHAR}, ",
        "#{wxId,jdbcType=VARCHAR}, #{beginTime,jdbcType=TIMESTAMP}, ",
        "#{endTime,jdbcType=TIMESTAMP}, #{agentLevelId,jdbcType=INTEGER}, ",
        "#{imgUrl,jdbcType=VARCHAR}, #{status,jdbcType=INTEGER}, ",
        "#{remark,jdbcType=VARCHAR})"
    })
    int insert(PfUserCertificate record);

    int insertSelective(PfUserCertificate record);

    List<PfUserCertificate> selectByExample(PfUserCertificateExample example);

    @Select({
        "select",
        "id, create_time, code, pf_user_sku_id, user_id, spu_id, sku_id, id_card, mobile, ",
        "wx_id, begin_time, end_time, agent_level_id, img_url, status, remark",
        "from pf_user_certificate",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("BaseResultMap")
    PfUserCertificate selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PfUserCertificate record, @Param("example") PfUserCertificateExample example);

    int updateByExample(@Param("record") PfUserCertificate record, @Param("example") PfUserCertificateExample example);

    int updateByPrimaryKeySelective(PfUserCertificate record);

    @Update({
        "update pf_user_certificate",
        "set create_time = #{createTime,jdbcType=TIMESTAMP},",
          "code = #{code,jdbcType=VARCHAR},",
          "pf_user_sku_id = #{pfUserSkuId,jdbcType=INTEGER},",
          "user_id = #{userId,jdbcType=BIGINT},",
          "spu_id = #{spuId,jdbcType=INTEGER},",
          "sku_id = #{skuId,jdbcType=INTEGER},",
          "id_card = #{idCard,jdbcType=VARCHAR},",
          "mobile = #{mobile,jdbcType=VARCHAR},",
          "wx_id = #{wxId,jdbcType=VARCHAR},",
          "begin_time = #{beginTime,jdbcType=TIMESTAMP},",
          "end_time = #{endTime,jdbcType=TIMESTAMP},",
          "agent_level_id = #{agentLevelId,jdbcType=INTEGER},",
          "img_url = #{imgUrl,jdbcType=VARCHAR},",
          "status = #{status,jdbcType=INTEGER},",
          "remark = #{remark,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(PfUserCertificate record);
}