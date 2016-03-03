package com.masiis.shop.dao.platform.order;

import com.masiis.shop.dao.po.PfBorder;

import java.util.List;

/**
 * Created by ZhaoLiang on 2016/3/3.
 */
public interface BOrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PfBorder record);

    PfBorder selectByPrimaryKey(Long id);

    List<PfBorder> selectAll();

    int updateByPrimaryKey(PfBorder record);
}
