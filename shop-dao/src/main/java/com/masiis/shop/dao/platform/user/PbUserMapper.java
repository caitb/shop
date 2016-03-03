package com.masiis.shop.dao.platform.user;


import com.masiis.shop.dao.po.PbUser;

import java.util.List;

/**
 * Created by caitingbiao on 2016/3/2.
 */
public interface PbUserMapper {

    /**
     * 根据id查询一条记录
     * @param id
     * @return
     */
    PbUser selectById(Long id);

    /**
     * 根据条件查询记录
     * @param pbUser
     * @return
     */
    List<PbUser> selectByCondition(PbUser pbUser);

    /**
     * 添加一条记录
     * @param pbUser
     */
    void insert(PbUser pbUser);

    /**
     * 根据id更新一条记录
     * @param pbUser
     */
    void updateById(PbUser pbUser);

    /**
     * 根据id删除一条记录
     * @param id
     */
    void deleteById(Long id);

}
