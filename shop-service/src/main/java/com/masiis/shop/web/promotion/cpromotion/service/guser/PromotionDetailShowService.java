package com.masiis.shop.web.promotion.cpromotion.service.guser;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.dao.beans.promotion.PromotionGiftInfo;
import com.masiis.shop.dao.beans.promotion.PromotionInfo;
import com.masiis.shop.dao.beans.promotion.PromotionRuleInfo;
import com.masiis.shop.dao.po.*;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 *  活动页面信息
 */
@Transactional
public class PromotionDetailShowService {

    private Log log = LogFactory.getLog(this.getClass());

    @Resource
    private SfUserPromotionService promoService;
    @Resource
    private SfUserPromotionRuleService ruleService;
    @Resource
    private SfUserPromotionGiftService giftService;
    @Resource
    private SfUserPromotionRecordService recordService;

    private static Integer fansQuantity;
    private static Boolean isMeetPromoRequire = false;

    public List<PromotionInfo> getAllPromoDetail(){
        log.info("获取活动数据----start");
        ComUser comUser = null;
        //获取用户粉丝数
        //获取所有的活动
        List<SfUserPromotion> userPromotions = promoService.selectAll();
        List<PromotionInfo> promotionInfos = new ArrayList<PromotionInfo>();
        log.info("获取所有的活动-----start");
        for(SfUserPromotion userPromotion:userPromotions){
            //获取此活动对应的规则
            log.info("获取此活动对应的规则-----活动id-----"+userPromotion.getId());
            List<SfUserPromotionRule> rules = ruleService.getPromoRuleByPromoId(userPromotion.getId());
            List<PromotionRuleInfo> ruleInfos = new ArrayList<>();
            for (SfUserPromotionRule rule : rules){
                //获取此规则对应的奖品信息
                log.info("获取此规则对应的奖品信息-----规则id-----"+rule.getId());
                List<PromotionGiftInfo> giftInfos = giftService.getPromoGiftInfoByPromoIdAndRuleId(userPromotion.getId(),rule.getId(),false);
                //生成某个规则信息
                PromotionRuleInfo ruleInfo = generatePromotionRuleInfo(comUser.getId(),userPromotion.getId(),rule);
                if (ruleInfo!=null&&giftInfos!=null&&giftInfos.size()>0){
                    log.info("奖品数量-------"+giftInfos.size());
                    ruleInfo.setGiftInfos(giftInfos);
                }
                ruleInfos.add(ruleInfo);
            }
            //生成某个活动信息
            log.info("粉丝数量-----"+fansQuantity);
            log.info("isMeetPromoRequire-----"+isMeetPromoRequire);
            PromotionInfo promotionInfo = new PromotionInfo();
            promotionInfo.setPromoId(userPromotion.getId());
            promotionInfo.setFansQuantity(fansQuantity);
            promotionInfo.setRuleInfos(ruleInfos);
            promotionInfo.setPresonType(userPromotion.getPersonType());
            promotionInfo.setMeetPromoRequire(isMeetPromoRequire);
            promotionInfos.add(promotionInfo);
        }
        log.info("获取所有的活动---end");
        log.info("获取活动数据----end");
        return promotionInfos;
    }

    private PromotionRuleInfo generatePromotionRuleInfo(Long userId, Integer promoId, SfUserPromotionRule rule){
        PromotionRuleInfo ruleInfo = new PromotionRuleInfo();
        ruleInfo.setPromoRuleId(rule.getId());
        ruleInfo.setPromotionFansQuantity(rule.getRuleValue());
        log.info("规则需要达到的粉丝数量-------"+rule.getRuleValue());
        log.info("用户目前的粉丝数量-------"+fansQuantity);
        if (rule.getRuleValue()-fansQuantity > 0){
            log.info("-----粉丝数量还没有达到要求-----");
            //粉丝数量还没有达到要求
            ruleInfo.setNeedFansQuantity(rule.getRuleValue()-fansQuantity);
            ruleInfo.setStatus(0);
        }else{
            SfUserPromotionRecord record =  recordService.getPromoRecordByUserIdAndPromoIdAndRuleId(userId,promoId,rule.getId());
            if (record!=null){
                //已领取
                log.info("-----奖品已领取------");
                ruleInfo.setStatus(2);
            }else{
                //达到活动要求未领取
                log.info("-----未领取------");
                isMeetPromoRequire = true;
                ruleInfo.setStatus(1);
            }
        }
        return  ruleInfo;
    }

}
