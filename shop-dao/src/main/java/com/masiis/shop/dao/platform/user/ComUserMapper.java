/*
 * ComUserMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-03 Created
 */
package com.masiis.shop.dao.platform.user;

import com.masiis.shop.dao.po.ComUser;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComUserMapper {

    /**
     * 新增用户
     * @param record
     * @return
     */
    int insert(ComUser record);

    /**
     * 根据id获取用户数据
     * @param id
     * @return
     */
    ComUser selectByPrimaryKey(Long id);

    /**
     * 获取所有用户
     * @return
     */
    List<ComUser> selectAll();

    /**
     * 修改用户资料
     * @param record
     * @return
     */
    int updateByPrimaryKey(ComUser record);

    /**
     * 更新手机号
     * @author hanzengzhi
     * @date 2016/3/19 17:35
     */
    int updatePhone(ComUser record);

    /**
     * 通过手机号获取用户
     * @param mobile
     * @return
     */
    ComUser selectByMobile(String mobile);

    String findByPid(Integer pid);

    /**
     * 根据openid查询用户
     *
     * @param openid
     * @return
     */
    ComUser selectByOpenid(String openid);

    List<ComUser> selectByCondition(ComUser comUser);

    List<ComUser> auditList(ComUser comUser);
}