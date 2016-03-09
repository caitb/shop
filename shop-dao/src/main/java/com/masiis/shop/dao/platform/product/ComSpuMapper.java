package com.masiis.shop.dao.platform.product;

import com.masiis.shop.dao.po.ComSpu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by hzzh on 2016/3/5.
 */
public interface ComSpuMapper {

    /**
     * 根据id查询一条记录
     * @param id
     * @return
     */
    ComSpu selectById(@Param("id")Integer id);

    /**
     * 根据条件查询记录
     * @param comSpu
     * @return
     */
    List<ComSpu> selectByCondition(@Param("comSpu")ComSpu comSpu);

    /**
     * 添加一条记录
     * @param comSpu
     */
    Integer insert(ComSpu comSpu);

    /**
     * 根据id更新一条记录
     * @param comSpu
     */
    void updateById(@Param("comSpu") ComSpu comSpu);

    /**
     * 根据id删除一条记录
     * @param id
     */
    void deleteById(@Param("id")Integer id);
}
