package com.masiis.shop.dao.platform.order;

import com.masiis.shop.dao.po.PfCorderConsignee;

import java.util.List;

/**
 * Created by cai_tb on 16/3/14.
 */
public interface PfCorderConsigneeMapper {

    PfCorderConsignee selectById(Long id);

    PfCorderConsignee selectByCorderId(Long corderId);

    List<PfCorderConsignee> selectByCondition(PfCorderConsignee pfCorderConsignee);

    void insert(PfCorderConsignee pfCorderConsignee);

    void updateById(PfCorderConsignee pfCorderConsignee);

    void deleteById(Long id);

}
