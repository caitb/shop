package com.masiis.shop.web.promotion.cpromotion.service.guser;

import com.masiis.shop.dao.mall.promotion.SfUserPromotionRuleMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 商城用户活动规则表 service
 */
@Service
public class SfUserPromotionRuleService {

    @Resource
    private SfUserPromotionRuleMapper sfUserPromotionRuleMapper;
}
