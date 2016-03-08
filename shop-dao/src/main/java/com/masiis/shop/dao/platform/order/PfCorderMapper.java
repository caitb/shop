package com.masiis.shop.dao.platform.order;


import com.masiis.shop.dao.po.PfCorder;
import org.apache.ibatis.annotations.Param;

/**
 * Created by 49134 on 2016/3/3.
 */
public interface PfCorderMapper {





    /**
     * 添加一条记录
     * @param pfCorder
     */
    void insert(@Param("pfCorder") PfCorder pfCorder);

    /**
     * 根据id更新一条记录
     * @param pfUserTrial
    void updateById(@Param("pfUserTrial") PfUserTrial pfUserTrial);

    *//**
     * 根据id删除一条记录
     * @param id
     *//*
    void deleteById(@Param("id") Long id);

    void reason(@Param("pfUserTrial") PfUserTrial pfUserTrial);*/

}
