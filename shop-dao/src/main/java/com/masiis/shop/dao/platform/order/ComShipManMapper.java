package com.masiis.shop.dao.platform.order;

import com.masiis.shop.dao.po.ComShipMan;

import java.util.List;

/**
 * Created by cai_tb on 16/3/16.
 */
public interface ComShipManMapper {

    ComShipMan selectById(Integer id);

    List<ComShipMan> selectAll();

    List<ComShipMan> selectByCondition(ComShipMan comShipMan);

    void insert(ComShipMan comShipMan);

    void updateById(ComShipMan comShipMan);

    void deleteById(Integer id);

}
