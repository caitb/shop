/*
 * PfCorderConsigneeMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-03 Created
 */
package po;

import com.masiis.shop.dao.PfCorderConsignee;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface PfCorderConsigneeMapper {
    @Delete({
        "delete from pf_corder_consignee",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into pf_corder_consignee (id, create_time, ",
        "pf_corder_id, user_id, ",
        "consignee, mobile, ",
        "province_id, province_name, ",
        "city_id, city_name, ",
        "region_id, region_name, ",
        "address, remark)",
        "values (#{id,jdbcType=BIGINT}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{pfCorderId,jdbcType=BIGINT}, #{userId,jdbcType=BIGINT}, ",
        "#{consignee,jdbcType=VARCHAR}, #{mobile,jdbcType=VARCHAR}, ",
        "#{provinceId,jdbcType=INTEGER}, #{provinceName,jdbcType=VARCHAR}, ",
        "#{cityId,jdbcType=INTEGER}, #{cityName,jdbcType=VARCHAR}, ",
        "#{regionId,jdbcType=INTEGER}, #{regionName,jdbcType=VARCHAR}, ",
        "#{address,jdbcType=VARCHAR}, #{remark,jdbcType=VARCHAR})"
    })
    int insert(PfCorderConsignee record);

    int insertSelective(PfCorderConsignee record);

    @Select({
        "select",
        "id, create_time, pf_corder_id, user_id, consignee, mobile, province_id, province_name, ",
        "city_id, city_name, region_id, region_name, address, remark",
        "from pf_corder_consignee",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("BaseResultMap")
    PfCorderConsignee selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PfCorderConsignee record);

    @Update({
        "update pf_corder_consignee",
        "set create_time = #{createTime,jdbcType=TIMESTAMP},",
          "pf_corder_id = #{pfCorderId,jdbcType=BIGINT},",
          "user_id = #{userId,jdbcType=BIGINT},",
          "consignee = #{consignee,jdbcType=VARCHAR},",
          "mobile = #{mobile,jdbcType=VARCHAR},",
          "province_id = #{provinceId,jdbcType=INTEGER},",
          "province_name = #{provinceName,jdbcType=VARCHAR},",
          "city_id = #{cityId,jdbcType=INTEGER},",
          "city_name = #{cityName,jdbcType=VARCHAR},",
          "region_id = #{regionId,jdbcType=INTEGER},",
          "region_name = #{regionName,jdbcType=VARCHAR},",
          "address = #{address,jdbcType=VARCHAR},",
          "remark = #{remark,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(PfCorderConsignee record);
}