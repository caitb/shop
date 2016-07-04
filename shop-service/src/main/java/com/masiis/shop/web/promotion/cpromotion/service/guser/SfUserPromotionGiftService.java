package com.masiis.shop.web.promotion.cpromotion.service.guser;

import com.masiis.shop.dao.mall.promotion.SfUserPromotionGiftMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 商城用户活动奖励表service
 */
@Service
public class SfUserPromotionGiftService {

    @Resource
    private SfUserPromotionGiftMapper sfUserPromotionGiftMapper;
}
