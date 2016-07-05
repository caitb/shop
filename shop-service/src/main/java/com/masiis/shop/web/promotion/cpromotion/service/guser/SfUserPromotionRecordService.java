package com.masiis.shop.web.promotion.cpromotion.service.guser;

import com.masiis.shop.dao.mall.promotion.SfUserPromotionRecordMapper;
import com.masiis.shop.dao.po.SfUserPromotionRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * C端用户活动参与记录表 service
 */
@Service
public class SfUserPromotionRecordService {

    @Resource
    private SfUserPromotionRecordMapper sfUserPromotionRecordMapper;

    public SfUserPromotionRecord getPromoRecordByUserIdAndPromoIdAndRuleId(Long userId,Integer promoId,Integer promoRuleId){
        return sfUserPromotionRecordMapper.getPromoRecordByUserIdAndPromoIdAndRuleId(userId,promoId,promoRuleId);
    }
}
