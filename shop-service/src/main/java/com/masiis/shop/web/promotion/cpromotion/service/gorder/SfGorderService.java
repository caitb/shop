package com.masiis.shop.web.promotion.cpromotion.service.gorder;

import com.masiis.shop.dao.mall.promotion.SfGorderMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * C端用户奖励订单表
 */
@Service
public class SfGorderService {

    @Resource
    private SfGorderMapper sfGorderMapper;
}
