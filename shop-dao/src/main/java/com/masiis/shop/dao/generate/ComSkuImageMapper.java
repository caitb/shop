/*
 * ComSkuImageMapper.java
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

public interface ComSkuImageMapper {
    int countByExample(ComSkuImageExample example);

    int deleteByExample(ComSkuImageExample example);

    @Delete({
        "delete from com_sku_image",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into com_sku_image (id, create_time, ",
        "create_man, spu_id, ",
        "sku_id, img_name, ",
        "img_url, modify_time, ",
        "modify_man, is_default, ",
        "sort, remark)",
        "values (#{id,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{createMan,jdbcType=BIGINT}, #{spuId,jdbcType=INTEGER}, ",
        "#{skuId,jdbcType=INTEGER}, #{imgName,jdbcType=VARCHAR}, ",
        "#{imgUrl,jdbcType=VARCHAR}, #{modifyTime,jdbcType=TIMESTAMP}, ",
        "#{modifyMan,jdbcType=BIGINT}, #{isDefault,jdbcType=INTEGER}, ",
        "#{sort,jdbcType=INTEGER}, #{remark,jdbcType=VARCHAR})"
    })
    int insert(ComSkuImage record);

    int insertSelective(ComSkuImage record);

    List<ComSkuImage> selectByExample(ComSkuImageExample example);

    @Select({
        "select",
        "id, create_time, create_man, spu_id, sku_id, img_name, img_url, modify_time, ",
        "modify_man, is_default, sort, remark",
        "from com_sku_image",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    ComSkuImage selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ComSkuImage record, @Param("example") ComSkuImageExample example);

    int updateByExample(@Param("record") ComSkuImage record, @Param("example") ComSkuImageExample example);

    int updateByPrimaryKeySelective(ComSkuImage record);

    @Update({
        "update com_sku_image",
        "set create_time = #{createTime,jdbcType=TIMESTAMP},",
          "create_man = #{createMan,jdbcType=BIGINT},",
          "spu_id = #{spuId,jdbcType=INTEGER},",
          "sku_id = #{skuId,jdbcType=INTEGER},",
          "img_name = #{imgName,jdbcType=VARCHAR},",
          "img_url = #{imgUrl,jdbcType=VARCHAR},",
          "modify_time = #{modifyTime,jdbcType=TIMESTAMP},",
          "modify_man = #{modifyMan,jdbcType=BIGINT},",
          "is_default = #{isDefault,jdbcType=INTEGER},",
          "sort = #{sort,jdbcType=INTEGER},",
          "remark = #{remark,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(ComSkuImage record);
}