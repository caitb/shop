package com.masiis.shop.web.promotion.cpromotion.service.guser;

import com.masiis.shop.dao.mall.promotion.SfUserPromotionGiftMapper;
import com.masiis.shop.dao.po.SfUserPromotionGift;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商城用户活动奖励表service
 */
@Service
public class SfUserPromotionGiftService {

    @Resource
    private SfUserPromotionGiftMapper sfUserPromotionGiftMapper;

    public List<SfUserPromotionGift> getPromoGiftByPromoIdAndRuleId(Integer promoId,Integer promoRuleId){
        return sfUserPromotionGiftMapper.getPromoGiftByPromoIdAndRuleId(promoId,promoRuleId);
    }
}
