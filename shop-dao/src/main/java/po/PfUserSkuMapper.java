/*
 * PfUserSkuMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-03 Created
 */
package po;

import com.masiis.shop.dao.PfUserSku;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface PfUserSkuMapper {
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

    @Select({
        "select",
        "id, create_time, code, pid, sku_id, agent_level_id, is_pay, is_certificate, ",
        "pf_corder_id, is_authorized, remark",
        "from pf_user_sku",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    PfUserSku selectByPrimaryKey(Integer id);

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