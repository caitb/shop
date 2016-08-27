/*
 * ComWxUserMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-04-08 Created
 */
package com.masiis.shop.dao.platform.user;

import com.masiis.shop.dao.po.ComWxUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ComWxUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ComWxUser record);

    ComWxUser selectByPrimaryKey(Long id);

    List<ComWxUser> selectAll();

    int updateByPrimaryKey(ComWxUser record);

    /**
     * 根据unionid来查询wx_user
     *
     * @param unionid
     * @return
     */
    List<ComWxUser> selectByUnionid(@Param("unionid") String unionid);

    /**
     * 根据union和appid唯一确定一条wxUser记录
     *
     * @param unionid
     * @param appid
     * @return
     */
    ComWxUser selectByUnionidAndAppid(@Param("unionid") String unionid,
                                      @Param("appid") String appid);

    List<ComWxUser> selectByUserId(@Param("comUserId") Long comUserId);

    ComWxUser selectByOpenidAndAppid(@Param("openId") String openId,
                                     @Param("appId") String appId);

    /**
     * 注册用户量
     * @param beginTime
     * @return
     */
    List<Map<String, Object>> registerCountUser(String beginTime);

    /**
     * 新增注册用户量
     * @param beginTime
     * @return
     */
    List<Map<String, Object>> registerNewCountUser(String beginTime);
}