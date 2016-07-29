package com.masiis.shop.web.promotion.cpromotion.service.gorder;

import com.masiis.shop.dao.mall.promotion.SfTurnTableGiftMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *  转盘奖品表service
 */
@Service
public class SfTurnTableGiftService {

    @Resource
    private SfTurnTableGiftMapper turnTableGiftMapper;
}
