/*
 * ComUserMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao;

import com.masiis.shop.dao.ComUser;
import com.masiis.shop.dao.ComUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface ComUserMapper {
    int countByExample(ComUserExample example);

    int deleteByExample(ComUserExample example);

    @Delete({
        "delete from com_user",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into com_user (id, create_time, ",
        "openid, wx_nk_name, ",
        "wx_id, wx_head_img, ",
        "access_token, refresh_token, ",
        "atoken_expire, rtoken_expire, ",
        "real_name, mobile, ",
        "id_card, id_card_front_url, ",
        "id_card_back_url, province_id, ",
        "province_name, city_id, ",
        "city_name, region_id, ",
        "region_name, address, ",
        "is_agent, sex)",
        "values (#{id,jdbcType=BIGINT}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{openid,jdbcType=VARCHAR}, #{wxNkName,jdbcType=VARCHAR}, ",
        "#{wxId,jdbcType=VARCHAR}, #{wxHeadImg,jdbcType=VARCHAR}, ",
        "#{accessToken,jdbcType=VARCHAR}, #{refreshToken,jdbcType=VARCHAR}, ",
        "#{atokenExpire,jdbcType=TIMESTAMP}, #{rtokenExpire,jdbcType=TIMESTAMP}, ",
        "#{realName,jdbcType=VARCHAR}, #{mobile,jdbcType=VARCHAR}, ",
        "#{idCard,jdbcType=VARCHAR}, #{idCardFrontUrl,jdbcType=VARCHAR}, ",
        "#{idCardBackUrl,jdbcType=VARCHAR}, #{provinceId,jdbcType=INTEGER}, ",
        "#{provinceName,jdbcType=VARCHAR}, #{cityId,jdbcType=INTEGER}, ",
        "#{cityName,jdbcType=VARCHAR}, #{regionId,jdbcType=INTEGER}, ",
        "#{regionName,jdbcType=VARCHAR}, #{address,jdbcType=VARCHAR}, ",
        "#{isAgent,jdbcType=INTEGER}, #{sex,jdbcType=INTEGER})"
    })
    int insert(ComUser record);

    int insertSelective(ComUser record);

    List<ComUser> selectByExample(ComUserExample example);

    @Select({
        "select",
        "id, create_time, openid, wx_nk_name, wx_id, wx_head_img, access_token, refresh_token, ",
        "atoken_expire, rtoken_expire, real_name, mobile, id_card, id_card_front_url, ",
        "id_card_back_url, province_id, province_name, city_id, city_name, region_id, ",
        "region_name, address, is_agent, sex",
        "from com_user",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("BaseResultMap")
    ComUser selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ComUser record, @Param("example") ComUserExample example);

    int updateByExample(@Param("record") ComUser record, @Param("example") ComUserExample example);

    int updateByPrimaryKeySelective(ComUser record);

    @Update({
        "update com_user",
        "set create_time = #{createTime,jdbcType=TIMESTAMP},",
          "openid = #{openid,jdbcType=VARCHAR},",
          "wx_nk_name = #{wxNkName,jdbcType=VARCHAR},",
          "wx_id = #{wxId,jdbcType=VARCHAR},",
          "wx_head_img = #{wxHeadImg,jdbcType=VARCHAR},",
          "access_token = #{accessToken,jdbcType=VARCHAR},",
          "refresh_token = #{refreshToken,jdbcType=VARCHAR},",
          "atoken_expire = #{atokenExpire,jdbcType=TIMESTAMP},",
          "rtoken_expire = #{rtokenExpire,jdbcType=TIMESTAMP},",
          "real_name = #{realName,jdbcType=VARCHAR},",
          "mobile = #{mobile,jdbcType=VARCHAR},",
          "id_card = #{idCard,jdbcType=VARCHAR},",
          "id_card_front_url = #{idCardFrontUrl,jdbcType=VARCHAR},",
          "id_card_back_url = #{idCardBackUrl,jdbcType=VARCHAR},",
          "province_id = #{provinceId,jdbcType=INTEGER},",
          "province_name = #{provinceName,jdbcType=VARCHAR},",
          "city_id = #{cityId,jdbcType=INTEGER},",
          "city_name = #{cityName,jdbcType=VARCHAR},",
          "region_id = #{regionId,jdbcType=INTEGER},",
          "region_name = #{regionName,jdbcType=VARCHAR},",
          "address = #{address,jdbcType=VARCHAR},",
          "is_agent = #{isAgent,jdbcType=INTEGER},",
          "sex = #{sex,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(ComUser record);
}