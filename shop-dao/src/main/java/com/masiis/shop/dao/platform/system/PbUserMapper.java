package com.masiis.shop.dao.platform.system;


import com.masiis.shop.dao.po.PbUser;
import org.apache.ibatis.annotations.Param;

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
    PbUser selectById(@Param("id")Long id);

    /**
     * 根据条件查询记录
     * @param pbUser
     * @return
     */
    List<PbUser> selectByCondition(@Param("pbUser")PbUser pbUser);

    /**
     * 添加一条记录
     * @param pbUser
     */
    void insert(@Param("pbUser")PbUser pbUser);

    /**
     * 根据id更新一条记录
     * @param pbUser
     */
    void updateById(@Param("pbUser")PbUser pbUser);

    /**
     * 根据id删除一条记录
     * @param id
     */
    void deleteById(@Param("id")Long id);

}
