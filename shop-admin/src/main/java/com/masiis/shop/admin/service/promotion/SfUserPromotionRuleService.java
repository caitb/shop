package com.masiis.shop.admin.service.promotion;

import com.masiis.shop.dao.mall.promotion.SfUserPromotionRuleMapper;
import com.masiis.shop.dao.po.SfUserPromotion;
import com.masiis.shop.dao.po.SfUserPromotionRule;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 活动规则　service
 */
@Service
public class SfUserPromotionRuleService {

    @Resource
    private SfUserPromotionRuleMapper sfUserPromotionRuleMapper;

    public SfUserPromotionRule insertOrUpdate(SfUserPromotionRule rule) {
        if(rule.getId() == null) {
            sfUserPromotionRuleMapper.insert(rule);
        } else {
            sfUserPromotionRuleMapper.updateByPrimaryKey(rule);
        }
        return rule;
    }

    /**
     * 删除无效的　rule
     */
    public Collection<Integer> removeInvalidRules(Integer promoId, List<SfUserPromotionRule> ruleList) {
        Set<Integer> ruleIdSet = new HashSet<>();
        for(SfUserPromotionRule rule : ruleList) {
            if(rule.getId() != null) {
                ruleIdSet.add(rule.getId());
            }
        }

        List<SfUserPromotionRule> dbRuleList = sfUserPromotionRuleMapper.getPromoRuleByPromoId(promoId);

        Set<Integer> dbRuleIdSet  = new HashSet<>();
        for(SfUserPromotionRule rule : dbRuleList) {
            dbRuleIdSet.add(rule.getId());
        }

        dbRuleIdSet.removeAll(ruleIdSet);   // 获取 db 中待删除的 rule id

        for(Object id : dbRuleIdSet) {
            sfUserPromotionRuleMapper.deleteByPrimaryKey((Integer)id);
        }

        return dbRuleIdSet;
    }

    public List<SfUserPromotionRule> createRuleList(Integer[] ruleValueArray, Integer[] ruleIdArray) {
        List<SfUserPromotionRule> ruleList = new LinkedList<>();

        if(ruleValueArray != null) {
            for(int i=0; i<ruleValueArray.length; i++) {
                Integer ruleValue = ruleValueArray[i];
                SfUserPromotionRule rule = new SfUserPromotionRule();
                rule.setRuleValue(ruleValue);
                rule.setType(0);    // 满赠

                if(ruleIdArray != null && i<ruleIdArray.length) {
                    rule.setId(ruleIdArray[i]);
                }
                ruleList.add(rule);
            }

        }

        return ruleList;
    }


}
