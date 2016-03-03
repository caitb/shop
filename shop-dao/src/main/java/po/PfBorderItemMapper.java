/*
 * PfBorderItemMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-03 Created
 */
package po;

import com.masiis.shop.dao.PfBorderItem;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface PfBorderItemMapper {
    @Delete({
        "delete from pf_border_item",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into pf_border_item (id, create_time, ",
        "pf_border_id, spu_id, ",
        "sku_id, sku_name, ",
        "quantity, unit_price, ",
        "total_price, is_comment, ",
        "is_return, remark)",
        "values (#{id,jdbcType=BIGINT}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{pfBorderId,jdbcType=BIGINT}, #{spuId,jdbcType=INTEGER}, ",
        "#{skuId,jdbcType=INTEGER}, #{skuName,jdbcType=VARCHAR}, ",
        "#{quantity,jdbcType=INTEGER}, #{unitPrice,jdbcType=DECIMAL}, ",
        "#{totalPrice,jdbcType=DECIMAL}, #{isComment,jdbcType=INTEGER}, ",
        "#{isReturn,jdbcType=INTEGER}, #{remark,jdbcType=VARCHAR})"
    })
    int insert(PfBorderItem record);

    int insertSelective(PfBorderItem record);

    @Select({
        "select",
        "id, create_time, pf_border_id, spu_id, sku_id, sku_name, quantity, unit_price, ",
        "total_price, is_comment, is_return, remark",
        "from pf_border_item",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("BaseResultMap")
    PfBorderItem selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PfBorderItem record);

    @Update({
        "update pf_border_item",
        "set create_time = #{createTime,jdbcType=TIMESTAMP},",
          "pf_border_id = #{pfBorderId,jdbcType=BIGINT},",
          "spu_id = #{spuId,jdbcType=INTEGER},",
          "sku_id = #{skuId,jdbcType=INTEGER},",
          "sku_name = #{skuName,jdbcType=VARCHAR},",
          "quantity = #{quantity,jdbcType=INTEGER},",
          "unit_price = #{unitPrice,jdbcType=DECIMAL},",
          "total_price = #{totalPrice,jdbcType=DECIMAL},",
          "is_comment = #{isComment,jdbcType=INTEGER},",
          "is_return = #{isReturn,jdbcType=INTEGER},",
          "remark = #{remark,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(PfBorderItem record);
}