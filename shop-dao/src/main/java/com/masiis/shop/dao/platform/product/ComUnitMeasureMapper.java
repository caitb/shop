package com.masiis.shop.dao.platform.product;

import com.masiis.shop.dao.po.ComUnitMeasure;

import java.util.List;

/**
 * 计量单位
 * Created by cai_tb on 16/3/30.
 */
public interface ComUnitMeasureMapper {

    ComUnitMeasure selectById(Integer id);

    List<ComUnitMeasure> selectByCondition(ComUnitMeasure comUnitMeasure);

    void insert(ComUnitMeasure comUnitMeasure);

    void updateById(ComUnitMeasure comUnitMeasure);

    void deleteById(Integer id);

}
