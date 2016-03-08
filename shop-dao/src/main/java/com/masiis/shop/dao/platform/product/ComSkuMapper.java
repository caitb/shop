/*
 * ComSkuMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-04 Created
 */
package com.masiis.shop.dao.platform.product;

import com.masiis.shop.dao.po.ComSku;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
    ComSku selectById(@Param("id")Long id);

    /**
     * 根据条件查询记录
     * @param comSku
     * @return
     */
    List<ComSku> selectByCondition(@Param("comSku")ComSku comSku);

    /**
     * 添加一条记录
     * @param comSku
     */
    Integer insert(@Param("comSku")ComSku comSku);

    /**
     * 根据id更新一条记录
     * @param comSku
     */
    void updateById(@Param("comSku")ComSku comSku);

    /**
     * 根据id删除一条记录
     * @param id
     */
    void deleteById(@Param("id")Long id);
}