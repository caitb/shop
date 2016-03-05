package com.masiis.shop.dao.platform.product;

import com.masiis.shop.dao.po.ComCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by cai_tb on 16/3/5.
 */
public interface ComCategoryMapper {

    /**
     * 根据id查询一条记录
     * @param id
     * @return
     */
    ComCategory selectById(@Param("id")Long id);

    /**
     * 根据条件查询记录
     * @param comCategory
     * @return
     */
    List<ComCategory> selectByCondition(@Param("comCategory")ComCategory comCategory);

    /**
     * 添加一条记录
     * @param comCategory
     */
    void insert(@Param("comCategory")ComCategory comCategory);

    /**
     * 根据id更新一条记录
     * @param comCategory
     */
    void updateById(@Param("comCategory")ComCategory comCategory);

    /**
     * 根据id删除一条记录
     * @param id
     */
    void deleteById(@Param("id")Long id);
}
