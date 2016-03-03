package com.masiis.shop.dao.platform.system;


import com.masiis.shop.dao.po.PbUserMenu;
import org.apache.ibatis.annotations.Param;

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
     * @param pbUserMenu
     * @return
     */
    List<PbUserMenu> selectByCondition(@Param("pbUserMenu") PbUserMenu pbUserMenu);

    /**
     * 添加一条记录
     * @param pbUserMenu
     */
    void insert(PbUserMenu pbUserMenu);

    /**
     * 根据id更新一条记录
     * @param pbUserMenu
     */
    void updateById(PbUserMenu pbUserMenu);

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
