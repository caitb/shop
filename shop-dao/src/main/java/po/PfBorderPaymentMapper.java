/*
 * PfBorderPaymentMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-03 Created
 */
package po;

import com.masiis.shop.dao.PfBorderPayment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface PfBorderPaymentMapper {
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

    @Select({
        "select",
        "id, create_time, pf_border_id, amount, pay_type_id, pay_type_name, is_enabled, ",
        "remark",
        "from pf_border_payment",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("BaseResultMap")
    PfBorderPayment selectByPrimaryKey(Long id);

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