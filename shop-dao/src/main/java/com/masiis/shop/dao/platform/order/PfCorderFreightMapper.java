package com.masiis.shop.dao.platform.order;

import com.masiis.shop.dao.po.PfCorderFreight;

import java.util.List;

/**
 * Created by cai_tb on 16/3/15.
 */
public interface PfCorderFreightMapper {

    PfCorderFreight selectById(Long id);

    List<PfCorderFreight> selectByCorderId(Long corderId);

    List<PfCorderFreight> selectByCondition(PfCorderFreight pfCorderFreight);

    void insert(PfCorderFreight pfCorderFreight);

    void updateById(PfCorderFreight pfCorderFreight);

    void deleteById(Long id);

}
