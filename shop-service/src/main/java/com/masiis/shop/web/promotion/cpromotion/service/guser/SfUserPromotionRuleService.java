package com.masiis.shop.web.promotion.cpromotion.service.guser;

import com.masiis.shop.dao.mall.promotion.SfUserPromotionRuleMapper;
import com.masiis.shop.dao.po.SfUserPromotionRule;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商城用户活动规则表 service
 */
@Service
public class SfUserPromotionRuleService {

    @Resource
    private SfUserPromotionRuleMapper sfUserPromotionRuleMapper;

    public List<SfUserPromotionRule> getPromoRuleByPromoId(Integer promoId){
        return sfUserPromotionRuleMapper.getPromoRuleByPromoId(promoId);
    }
}
