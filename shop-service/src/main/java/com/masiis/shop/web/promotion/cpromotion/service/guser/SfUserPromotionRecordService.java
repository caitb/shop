package com.masiis.shop.web.promotion.cpromotion.service.guser;

import com.masiis.shop.dao.mall.promotion.SfUserPromotionRecordMapper;
import com.masiis.shop.dao.po.SfUserPromotionRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * C端用户活动参与记录表 service
 */
@Service
@Transactional
public class SfUserPromotionRecordService {

    @Resource
    private SfUserPromotionRecordMapper sfUserPromotionRecordMapper;

    public SfUserPromotionRecord getPromoRecordByUserIdAndPromoIdAndRuleId(Long userId,Integer promoId,Integer promoRuleId){
        return sfUserPromotionRecordMapper.getPromoRecordByUserIdAndPromoIdAndRuleId(userId,promoId,promoRuleId);
    }

    public int addSfUserPromotionRecord(Long userId,Integer promoId,Integer promoRuleId){
        SfUserPromotionRecord record = new SfUserPromotionRecord();
        record.setCreateTime(new Date());
        record.setCreateMan(userId);
        record.setUserId(userId);
        record.setPromoId(promoId);
        record.setPromoRuleId(promoRuleId);
        record.setStatus(1);//已参与
        record.setRemark("领取完奖励插入记录");
       return sfUserPromotionRecordMapper.insert(record);
    }
}
