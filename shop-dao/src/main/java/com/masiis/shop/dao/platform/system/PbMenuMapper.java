package com.masiis.shop.dao.platform.system;


import com.masiis.shop.dao.po.PbMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by cai_tb on 16/3/3.
 */
public interface PbMenuMapper {

    /**
     * 根据id查询一条记录
     * @param id
     * @return
     */
    PbMenu selectById(@Param("id")Long id);

    /**
     * 根据条件查询记录
     * @param pbMenu
     * @return
     */
    List<PbMenu> selectByCondition(@Param("pbMenu")PbMenu pbMenu);

    /**
     * 添加一条记录
     * @param pbMenu
     */
    void insert(@Param("pbMenu")PbMenu pbMenu);

    /**
     * 根据id更新一条记录
     * @param pbMenu
     */
    void updateById(@Param("pbMenu")PbMenu pbMenu);

    /**
     * 根据id删除一条记录
     * @param id
     */
    void deleteById(@Param("id")Long id);
}
