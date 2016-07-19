package com.masiis.shop.web.promotion.cpromotion.service.guser;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.dao.beans.promotion.PromotionGiftInfo;
import com.masiis.shop.dao.beans.promotion.PromotionInfo;
import com.masiis.shop.dao.beans.promotion.PromotionRuleInfo;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.mall.service.shop.SfShopService;
import com.masiis.shop.web.mall.service.user.SfUserRelationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  活动页面信息
 */
@Service
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
    @Resource
    private SfUserRelationService userRelationService;

    private static Integer fansQuantity;
    private static Boolean isMeetPromoRequire = false;

    public Map<String,Object> getAllPromoDetail(ComUser comUser){
        log.info("获取活动数据----start");
        Map<String,Object> map = new HashMap<>();
        //获取所有进行中的活动
        List<SfUserPromotion> userPromotions = promoService.getPromotionByStatus(0);

        log.info("用户id-----"+comUser.getId()+"----粉丝数-----"+fansQuantity);
        List<PromotionInfo> promotionInfos = new ArrayList<>();
        log.info("获取所有的活动-----start");
        for(SfUserPromotion userPromotion:userPromotions){
            //获取用户粉丝数或代言数
            Map<String, Integer> _map = userRelationService.getFansOrSpokesManNumByUserId(comUser.getId(), userPromotion.getPersonType());
            if (_map!=null){
                fansQuantity = (Integer) _map.get("maxNum");
                log.info("获取代言人代言的小铺的最大粉丝数-------"+fansQuantity);
            }else{
                log.info("根据用户获取小铺代言的小铺map为null-----用户id----"+comUser.getId());
                fansQuantity = 0;
            }
            //获取此活动对应的规则
            log.info("获取此活动对应的规则-----活动id-----"+userPromotion.getId());
            List<SfUserPromotionRule> rules = ruleService.getPromoRuleByPromoId(userPromotion.getId());
            List<PromotionRuleInfo> ruleInfos = new ArrayList<>();
            for (SfUserPromotionRule rule : rules){
                //获取此规则对应的奖品信息
                log.info("获取此规则对应的奖品信息-----规则id-----"+rule.getId());
                List<PromotionGiftInfo> giftInfos = giftService.getPromoGiftInfosByPromoIdAndRuleId(userPromotion.getId(),rule.getId(),true);
                //生成某个规则信息
                PromotionRuleInfo ruleInfo = generatePromotionRuleInfo(comUser.getId(),userPromotion.getId(),rule,giftInfos);
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
            promotionInfo.setRuleInfos(ruleInfos);
            promotionInfo.setPresonType(userPromotion.getPersonType());
            if (userPromotion.getPersonType()==0){
                promotionInfo.setPresonTypeName("粉丝");
            }else if (userPromotion.getPersonType()==1){
                promotionInfo.setPresonTypeName("代言人");
            }
            promotionInfo.setMeetPromoRequire(isMeetPromoRequire);
            promotionInfo.setBeginTime(DateUtil.Date2String(userPromotion.getBeginTime(),DateUtil.SQL_TIME_FMT));
            promotionInfo.setEndTime(DateUtil.Date2String(userPromotion.getEndTime(),DateUtil.SQL_TIME_FMT));
            promotionInfos.add(promotionInfo);
        }
        map.put("promotionInfos",promotionInfos);
        map.put("fansQuantity",fansQuantity);
        log.info("获取所有的活动---end");
        log.info("获取活动数据----end");
        return map;
    }

    private PromotionRuleInfo generatePromotionRuleInfo(Long userId, Integer promoId, SfUserPromotionRule rule,List<PromotionGiftInfo> giftInfos){
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
            Boolean isEnoughQuantity = true;//此规则是否还有足够的商品供领取
            for (PromotionGiftInfo promotionGiftInfo:giftInfos){
                if (!promotionGiftInfo.getIsEnoughQuantity()){
                    isEnoughQuantity = false;
                }
            }
            if (isEnoughQuantity){
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
            }else{
                //奖品领取完无法领取
                ruleInfo.setStatus(3);
            }

        }
        return  ruleInfo;
    }

}
