/*
 * PfSkuStockMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-03 Created
 */
package po;

import com.masiis.shop.dao.PfSkuStock;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface PfSkuStockMapper {
    @Delete({
        "delete from pf_sku_stock",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into pf_sku_stock (id, create_time, ",
        "supplier_id, spu_id, ",
        "sku_id, stock, frozen_stock, ",
        "remark)",
        "values (#{id,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{supplierId,jdbcType=INTEGER}, #{spuId,jdbcType=INTEGER}, ",
        "#{skuId,jdbcType=INTEGER}, #{stock,jdbcType=INTEGER}, #{frozenStock,jdbcType=INTEGER}, ",
        "#{remark,jdbcType=VARCHAR})"
    })
    int insert(PfSkuStock record);

    int insertSelective(PfSkuStock record);

    @Select({
        "select",
        "id, create_time, supplier_id, spu_id, sku_id, stock, frozen_stock, remark",
        "from pf_sku_stock",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    PfSkuStock selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PfSkuStock record);

    @Update({
        "update pf_sku_stock",
        "set create_time = #{createTime,jdbcType=TIMESTAMP},",
          "supplier_id = #{supplierId,jdbcType=INTEGER},",
          "spu_id = #{spuId,jdbcType=INTEGER},",
          "sku_id = #{skuId,jdbcType=INTEGER},",
          "stock = #{stock,jdbcType=INTEGER},",
          "frozen_stock = #{frozenStock,jdbcType=INTEGER},",
          "remark = #{remark,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(PfSkuStock record);
}