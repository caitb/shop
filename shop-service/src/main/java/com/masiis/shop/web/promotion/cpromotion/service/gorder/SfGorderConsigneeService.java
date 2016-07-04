package com.masiis.shop.web.promotion.cpromotion.service.gorder;

import com.masiis.shop.dao.mall.promotion.SfGorderConsigneeMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * C端用户奖励订单收货人表service
 */
@Service
public class SfGorderConsigneeService {

    @Resource
    private SfGorderConsigneeMapper sfGorderConsigneeMapper;
}
