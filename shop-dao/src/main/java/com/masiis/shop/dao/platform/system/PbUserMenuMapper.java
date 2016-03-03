package com.masiis.shop.dao.platform.system;


import po.PbUserMenu;

import java.util.List;

/**
 * Created by cai_tb on 16/3/3.
 */
public interface PbUserMenuMapper {

    /**
     * 根据id查询一条记录
     * @param id
     * @return
     */
    PbUserMenu selectById(Long id);

    /**
     * 根据条件查询记录
     * @param pbUser
     * @return
     */
    List<PbUserMenu> selectByCondition(PbUserMenu pbUser);

    /**
     * 添加一条记录
     * @param pbUser
     */
    void insert(PbUserMenu pbUser);

    /**
     * 根据id更新一条记录
     * @param pbUser
     */
    void updateById(PbUserMenu pbUser);

    /**
     * 根据id删除一条记录
     * @param id
     */
    void deleteById(Long id);

    /**
     * 根据pb_user_id删除记录
     * @param pbUserId
     */
    void deleteByPbUserId(Long pbUserId);
}
