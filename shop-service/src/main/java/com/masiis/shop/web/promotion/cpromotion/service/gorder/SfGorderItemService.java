package com.masiis.shop.web.promotion.cpromotion.service.gorder;

import com.masiis.shop.dao.mall.promotion.SfGorderItemMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * C端用户奖励订单奖励明细表 service
 */
@Service
public class SfGorderItemService {

    @Resource
    private SfGorderItemMapper sfGorderItemMapper;
}
