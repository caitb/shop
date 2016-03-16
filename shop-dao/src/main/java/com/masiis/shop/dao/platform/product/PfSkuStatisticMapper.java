package com.masiis.shop.dao.platform.product;

import com.masiis.shop.dao.po.PfSkuStatistic;

import java.util.List;

/**
 * Created by cai_tb on 16/3/10.
 */
public interface PfSkuStatisticMapper {

    /**
     * 根据id查找记录
     * @param id
     * @return
     */
    PfSkuStatistic selectById(Integer id);

    /**
     * 根据条件查询记录
     * @param pfSkuStatistic
     * @return
     */
    List<PfSkuStatistic> selectByCondition(PfSkuStatistic pfSkuStatistic);

    /**
     * 保存记录
     * @param pfSkuStatistic
     */
    void insert(PfSkuStatistic pfSkuStatistic);

    /**
     * 根据id更新记录
     * @param pfSkuStatistic
     */
    void updateById(PfSkuStatistic pfSkuStatistic);

    /**
     * 根据id删除记录
     * @param id
     */
    void deleteById(Integer id);

    PfSkuStatistic selectBySkuId(Integer skuId);
}
