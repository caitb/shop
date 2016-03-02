/*
 * PfCorderPaymentMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao.generate;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface PfCorderPaymentMapper {
    int countByExample(PfCorderPaymentExample example);

    int deleteByExample(PfCorderPaymentExample example);

    @Delete({
        "delete from pf_corder_payment",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into pf_corder_payment (id, create_time, ",
        "pf_corder_id, amount, ",
        "pay_type_id, pay_type_name, ",
        "is_enabled, remark)",
        "values (#{id,jdbcType=BIGINT}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{pfCorderId,jdbcType=BIGINT}, #{amount,jdbcType=DECIMAL}, ",
        "#{payTypeId,jdbcType=INTEGER}, #{payTypeName,jdbcType=VARCHAR}, ",
        "#{isEnabled,jdbcType=INTEGER}, #{remark,jdbcType=VARCHAR})"
    })
    int insert(PfCorderPayment record);

    int insertSelective(PfCorderPayment record);

    List<PfCorderPayment> selectByExample(PfCorderPaymentExample example);

    @Select({
        "select",
        "id, create_time, pf_corder_id, amount, pay_type_id, pay_type_name, is_enabled, ",
        "remark",
        "from pf_corder_payment",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("BaseResultMap")
    PfCorderPayment selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PfCorderPayment record, @Param("example") PfCorderPaymentExample example);

    int updateByExample(@Param("record") PfCorderPayment record, @Param("example") PfCorderPaymentExample example);

    int updateByPrimaryKeySelective(PfCorderPayment record);

    @Update({
        "update pf_corder_payment",
        "set create_time = #{createTime,jdbcType=TIMESTAMP},",
          "pf_corder_id = #{pfCorderId,jdbcType=BIGINT},",
          "amount = #{amount,jdbcType=DECIMAL},",
          "pay_type_id = #{payTypeId,jdbcType=INTEGER},",
          "pay_type_name = #{payTypeName,jdbcType=VARCHAR},",
          "is_enabled = #{isEnabled,jdbcType=INTEGER},",
          "remark = #{remark,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(PfCorderPayment record);
}