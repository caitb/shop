package com.masiis.shop.web.promotion.cpromotion.service.guser;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.dao.mall.promotion.SfUserPromotionRuleMapper;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.SfUserPromotionRule;
import com.masiis.shop.web.mall.service.user.SfUserRelationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 商城用户活动规则表 service
 */
@Service
public class SfUserPromotionRuleService {

    private Log log = LogFactory.getLog(this.getClass());
    @Resource
    private SfUserPromotionRuleMapper sfUserPromotionRuleMapper;
    @Resource
    private SfUserRelationService userRelationService;

    public Boolean isMayReceiveReward(ComUser comUser,Integer promoRuleId){
        Map<String, Integer> _map = userRelationService.getFansOrSpokesManNumByUserId(comUser.getId(), 1);
        if (_map!=null){
            Integer fansQuantity = (Integer) _map.get("maxNum");
            SfUserPromotionRule rule = sfUserPromotionRuleMapper.selectByPrimaryKey(promoRuleId);
            if (rule!=null){
                log.info("规则要求的数值-------"+rule.getRuleValue()+"-----代言人所有小铺的最高粉丝数量-----"+fansQuantity);
                if (rule.getRuleValue()<=fansQuantity){
                    return true;
                }else{
                    return false;
                }
            }
        }
        return true;
    }

    public List<SfUserPromotionRule> getPromoRuleByPromoId(Integer promoId){
        return sfUserPromotionRuleMapper.getPromoRuleByPromoId(promoId);
    }
}
