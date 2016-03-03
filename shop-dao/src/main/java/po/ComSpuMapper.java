/*
 * ComSpuMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-03 Created
 */
package po;

import com.masiis.shop.dao.ComSpu;
import com.masiis.shop.dao.ComSpuKey;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface ComSpuMapper {
    @Delete({
        "delete from com_spu",
        "where id = #{id,jdbcType=INTEGER}",
          "and name = #{name,jdbcType=VARCHAR}",
          "and create_time = #{createTime,jdbcType=TIMESTAMP}",
          "and create_man = #{createMan,jdbcType=BIGINT}",
          "and category_id = #{categoryId,jdbcType=INTEGER}",
          "and category_name = #{categoryName,jdbcType=VARCHAR}",
          "and status = #{status,jdbcType=INTEGER}",
          "and is_sale = #{isSale,jdbcType=INTEGER}",
          "and is_delete = #{isDelete,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(ComSpuKey key);

    @Insert({
        "insert into com_spu (id, name, ",
        "create_time, create_man, ",
        "category_id, category_name, ",
        "status, is_sale, ",
        "is_delete, brand_id, ",
        "up_time, down_time, ",
        "modify_time, modify_man, ",
        "slogan, is_trial, ",
        "content, weight, ",
        "pack_length, pack_width, ",
        "pack_height, remark)",
        "values (#{id,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{createMan,jdbcType=BIGINT}, ",
        "#{categoryId,jdbcType=INTEGER}, #{categoryName,jdbcType=VARCHAR}, ",
        "#{status,jdbcType=INTEGER}, #{isSale,jdbcType=INTEGER}, ",
        "#{isDelete,jdbcType=INTEGER}, #{brandId,jdbcType=INTEGER}, ",
        "#{upTime,jdbcType=TIMESTAMP}, #{downTime,jdbcType=TIMESTAMP}, ",
        "#{modifyTime,jdbcType=TIMESTAMP}, #{modifyMan,jdbcType=BIGINT}, ",
        "#{slogan,jdbcType=VARCHAR}, #{isTrial,jdbcType=INTEGER}, ",
        "#{content,jdbcType=VARCHAR}, #{weight,jdbcType=DECIMAL}, ",
        "#{packLength,jdbcType=DECIMAL}, #{packWidth,jdbcType=DECIMAL}, ",
        "#{packHeight,jdbcType=DECIMAL}, #{remark,jdbcType=VARCHAR})"
    })
    int insert(ComSpu record);

    int insertSelective(ComSpu record);

    @Select({
        "select",
        "id, name, create_time, create_man, category_id, category_name, status, is_sale, ",
        "is_delete, brand_id, up_time, down_time, modify_time, modify_man, slogan, is_trial, ",
        "content, weight, pack_length, pack_width, pack_height, remark",
        "from com_spu",
        "where id = #{id,jdbcType=INTEGER}",
          "and name = #{name,jdbcType=VARCHAR}",
          "and create_time = #{createTime,jdbcType=TIMESTAMP}",
          "and create_man = #{createMan,jdbcType=BIGINT}",
          "and category_id = #{categoryId,jdbcType=INTEGER}",
          "and category_name = #{categoryName,jdbcType=VARCHAR}",
          "and status = #{status,jdbcType=INTEGER}",
          "and is_sale = #{isSale,jdbcType=INTEGER}",
          "and is_delete = #{isDelete,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    ComSpu selectByPrimaryKey(ComSpuKey key);

    int updateByPrimaryKeySelective(ComSpu record);

    @Update({
        "update com_spu",
        "set brand_id = #{brandId,jdbcType=INTEGER},",
          "up_time = #{upTime,jdbcType=TIMESTAMP},",
          "down_time = #{downTime,jdbcType=TIMESTAMP},",
          "modify_time = #{modifyTime,jdbcType=TIMESTAMP},",
          "modify_man = #{modifyMan,jdbcType=BIGINT},",
          "slogan = #{slogan,jdbcType=VARCHAR},",
          "is_trial = #{isTrial,jdbcType=INTEGER},",
          "content = #{content,jdbcType=VARCHAR},",
          "weight = #{weight,jdbcType=DECIMAL},",
          "pack_length = #{packLength,jdbcType=DECIMAL},",
          "pack_width = #{packWidth,jdbcType=DECIMAL},",
          "pack_height = #{packHeight,jdbcType=DECIMAL},",
          "remark = #{remark,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}",
          "and name = #{name,jdbcType=VARCHAR}",
          "and create_time = #{createTime,jdbcType=TIMESTAMP}",
          "and create_man = #{createMan,jdbcType=BIGINT}",
          "and category_id = #{categoryId,jdbcType=INTEGER}",
          "and category_name = #{categoryName,jdbcType=VARCHAR}",
          "and status = #{status,jdbcType=INTEGER}",
          "and is_sale = #{isSale,jdbcType=INTEGER}",
          "and is_delete = #{isDelete,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(ComSpu record);
}