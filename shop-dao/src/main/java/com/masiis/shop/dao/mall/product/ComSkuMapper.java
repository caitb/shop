/*
 * ComSkuMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-04 Created
 */
package com.masiis.shop.dao.mall.product;

import com.masiis.shop.dao.po.ComSku;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComSkuMapper {
    int deleteByPrimaryKey(Integer id);

    ComSku selectByPrimaryKey(Integer id);

    List<ComSku> selectAll();

    int updateByPrimaryKey(ComSku record);

    /**
     * 查找申请商品
     * @param skuId
     * @return
     */
    ComSku findBySkuId(@Param("skuId") Integer skuId);











    /**
     * 根据id查询一条记录
     * @param id
     * @return
     */
    ComSku selectById(Integer id);

    /**
     * 根据条件查询记录
     * @param comSku
     * @return
     */
    List<ComSku> selectByCondition(ComSku comSku);

    /**
     * 添加一条记录
     * @param comSku
     */
    Integer insert(ComSku comSku);

    /**
     * 根据id更新一条记录
     * @param comSku
     */
    void updateById(ComSku comSku);

    /**
     * 根据id删除一条记录
     * @param id
     */
    void deleteById(@Param("id") Long id);
}