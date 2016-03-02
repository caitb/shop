/*
 * PfBorderMapper.java
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

public interface PfBorderMapper {
    int countByExample(PfBorderExample example);

    int deleteByExample(PfBorderExample example);

    @Delete({
        "delete from pf_border",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into pf_border (id, create_time, ",
        "create_man, order_code, ",
        "user_id, user_pid, user_massage, ",
        "supplier_id, modify_time, ",
        "modify_man, receivable_amount, ",
        "order_amount, product_amount, ",
        "ship_amount, pay_amount, ",
        "pay_time, ship_man_id, ",
        "ship_man_name, delivery_time, ",
        "ship_remark, replace_order_id, ",
        "order_status, ship_status, ",
        "pay_status, is_ship, ",
        "is_replace, is_receipt, ",
        "receipt_time, remark)",
        "values (#{id,jdbcType=BIGINT}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{createMan,jdbcType=BIGINT}, #{orderCode,jdbcType=VARCHAR}, ",
        "#{userId,jdbcType=BIGINT}, #{userPid,jdbcType=BIGINT}, #{userMassage,jdbcType=VARCHAR}, ",
        "#{supplierId,jdbcType=INTEGER}, #{modifyTime,jdbcType=TIMESTAMP}, ",
        "#{modifyMan,jdbcType=BIGINT}, #{receivableAmount,jdbcType=DECIMAL}, ",
        "#{orderAmount,jdbcType=DECIMAL}, #{productAmount,jdbcType=DECIMAL}, ",
        "#{shipAmount,jdbcType=DECIMAL}, #{payAmount,jdbcType=DECIMAL}, ",
        "#{payTime,jdbcType=TIMESTAMP}, #{shipManId,jdbcType=INTEGER}, ",
        "#{shipManName,jdbcType=VARCHAR}, #{deliveryTime,jdbcType=TIMESTAMP}, ",
        "#{shipRemark,jdbcType=VARCHAR}, #{replaceOrderId,jdbcType=BIGINT}, ",
        "#{orderStatus,jdbcType=INTEGER}, #{shipStatus,jdbcType=INTEGER}, ",
        "#{payStatus,jdbcType=INTEGER}, #{isShip,jdbcType=INTEGER}, ",
        "#{isReplace,jdbcType=INTEGER}, #{isReceipt,jdbcType=INTEGER}, ",
        "#{receiptTime,jdbcType=TIMESTAMP}, #{remark,jdbcType=VARCHAR})"
    })
    int insert(PfBorder record);

    int insertSelective(PfBorder record);

    List<PfBorder> selectByExample(PfBorderExample example);

    @Select({
        "select",
        "id, create_time, create_man, order_code, user_id, user_pid, user_massage, supplier_id, ",
        "modify_time, modify_man, receivable_amount, order_amount, product_amount, ship_amount, ",
        "pay_amount, pay_time, ship_man_id, ship_man_name, delivery_time, ship_remark, ",
        "replace_order_id, order_status, ship_status, pay_status, is_ship, is_replace, ",
        "is_receipt, receipt_time, remark",
        "from pf_border",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("BaseResultMap")
    PfBorder selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PfBorder record, @Param("example") PfBorderExample example);

    int updateByExample(@Param("record") PfBorder record, @Param("example") PfBorderExample example);

    int updateByPrimaryKeySelective(PfBorder record);

    @Update({
        "update pf_border",
        "set create_time = #{createTime,jdbcType=TIMESTAMP},",
          "create_man = #{createMan,jdbcType=BIGINT},",
          "order_code = #{orderCode,jdbcType=VARCHAR},",
          "user_id = #{userId,jdbcType=BIGINT},",
          "user_pid = #{userPid,jdbcType=BIGINT},",
          "user_massage = #{userMassage,jdbcType=VARCHAR},",
          "supplier_id = #{supplierId,jdbcType=INTEGER},",
          "modify_time = #{modifyTime,jdbcType=TIMESTAMP},",
          "modify_man = #{modifyMan,jdbcType=BIGINT},",
          "receivable_amount = #{receivableAmount,jdbcType=DECIMAL},",
          "order_amount = #{orderAmount,jdbcType=DECIMAL},",
          "product_amount = #{productAmount,jdbcType=DECIMAL},",
          "ship_amount = #{shipAmount,jdbcType=DECIMAL},",
          "pay_amount = #{payAmount,jdbcType=DECIMAL},",
          "pay_time = #{payTime,jdbcType=TIMESTAMP},",
          "ship_man_id = #{shipManId,jdbcType=INTEGER},",
          "ship_man_name = #{shipManName,jdbcType=VARCHAR},",
          "delivery_time = #{deliveryTime,jdbcType=TIMESTAMP},",
          "ship_remark = #{shipRemark,jdbcType=VARCHAR},",
          "replace_order_id = #{replaceOrderId,jdbcType=BIGINT},",
          "order_status = #{orderStatus,jdbcType=INTEGER},",
          "ship_status = #{shipStatus,jdbcType=INTEGER},",
          "pay_status = #{payStatus,jdbcType=INTEGER},",
          "is_ship = #{isShip,jdbcType=INTEGER},",
          "is_replace = #{isReplace,jdbcType=INTEGER},",
          "is_receipt = #{isReceipt,jdbcType=INTEGER},",
          "receipt_time = #{receiptTime,jdbcType=TIMESTAMP},",
          "remark = #{remark,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(PfBorder record);
}