/*
 * PfBorderPaymentMapper.java
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

public interface PfBorderPaymentMapper {
    int countByExample(PfBorderPaymentExample example);

    int deleteByExample(PfBorderPaymentExample example);

    @Delete({
        "delete from pf_border_payment",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into pf_border_payment (id, create_time, ",
        "pf_border_id, amount, ",
        "pay_type_id, pay_type_name, ",
        "is_enabled, remark)",
        "values (#{id,jdbcType=BIGINT}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{pfBorderId,jdbcType=BIGINT}, #{amount,jdbcType=DECIMAL}, ",
        "#{payTypeId,jdbcType=INTEGER}, #{payTypeName,jdbcType=VARCHAR}, ",
        "#{isEnabled,jdbcType=INTEGER}, #{remark,jdbcType=VARCHAR})"
    })
    int insert(PfBorderPayment record);

    int insertSelective(PfBorderPayment record);

    List<PfBorderPayment> selectByExample(PfBorderPaymentExample example);

    @Select({
        "select",
        "id, create_time, pf_border_id, amount, pay_type_id, pay_type_name, is_enabled, ",
        "remark",
        "from pf_border_payment",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("BaseResultMap")
    PfBorderPayment selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PfBorderPayment record, @Param("example") PfBorderPaymentExample example);

    int updateByExample(@Param("record") PfBorderPayment record, @Param("example") PfBorderPaymentExample example);

    int updateByPrimaryKeySelective(PfBorderPayment record);

    @Update({
        "update pf_border_payment",
        "set create_time = #{createTime,jdbcType=TIMESTAMP},",
          "pf_border_id = #{pfBorderId,jdbcType=BIGINT},",
          "amount = #{amount,jdbcType=DECIMAL},",
          "pay_type_id = #{payTypeId,jdbcType=INTEGER},",
          "pay_type_name = #{payTypeName,jdbcType=VARCHAR},",
          "is_enabled = #{isEnabled,jdbcType=INTEGER},",
          "remark = #{remark,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(PfBorderPayment record);
}