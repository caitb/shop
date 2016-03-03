/*
 * ComBrandMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-03 Created
 */
package po;

import com.masiis.shop.dao.ComBrand;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface ComBrandMapper {
    @Delete({
        "delete from com_brand",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into com_brand (id, create_time, ",
        "cname, ename, logo_url, ",
        "remark)",
        "values (#{id,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{cname,jdbcType=VARCHAR}, #{ename,jdbcType=VARCHAR}, #{logoUrl,jdbcType=VARCHAR}, ",
        "#{remark,jdbcType=VARCHAR})"
    })
    int insert(ComBrand record);

    int insertSelective(ComBrand record);

    @Select({
        "select",
        "id, create_time, cname, ename, logo_url, remark",
        "from com_brand",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    ComBrand selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ComBrand record);

    @Update({
        "update com_brand",
        "set create_time = #{createTime,jdbcType=TIMESTAMP},",
          "cname = #{cname,jdbcType=VARCHAR},",
          "ename = #{ename,jdbcType=VARCHAR},",
          "logo_url = #{logoUrl,jdbcType=VARCHAR},",
          "remark = #{remark,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(ComBrand record);
}