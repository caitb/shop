package com.masiis.shop.web.promotion.cpromotion.service.guser;

import com.masiis.shop.dao.mall.promotion.SfUserTurnTableItemMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户转盘明细 service
 */
@Service
public class SfUserTurnTableItemService {

    @Resource
    private SfUserTurnTableItemMapper userTurnTableItemMapper;
}
