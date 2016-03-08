/*
 * ComUserMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-03 Created
 */
package com.masiis.shop.dao.platform.user;

import com.masiis.shop.dao.po.ComUser;
import java.util.List;

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

    String findByParentId(Long userId);
}