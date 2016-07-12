package com.masiis.shop.admin.service.promotion;

        import com.github.pagehelper.PageHelper;
        import com.masiis.shop.common.util.PromotionMakeUtils;
        import com.masiis.shop.dao.mall.promotion.SfUserPromotionGiftMapper;
        import com.masiis.shop.dao.mall.promotion.SfUserPromotionMapper;
        import com.masiis.shop.dao.mall.promotion.SfUserPromotionRuleMapper;
        import com.masiis.shop.dao.po.*;
        import com.sun.corba.se.spi.ior.ObjectKey;
        import org.springframework.stereotype.Service;

        import javax.annotation.Resource;
        import javax.servlet.http.HttpServletRequest;
        import java.util.*;

/**
 * 活动 service
 */
@Service
public class SfUserPromotionService {

    @Resource
    private SfUserPromotionMapper sfUserPromotionMapper;
    @Resource
    private SfUserPromotionRuleMapper sfUserPromotionRuleMapper;
    @Resource
    private SfUserPromotionGiftMapper sfUserPromotionGiftMapper;

    @Resource
    private SfUserPromotionRuleService sfUserPromotionRuleService;
    @Resource
    private SfUserPromotionGiftService sfUserPromotionGiftService;

    public void savePromotion(SfUserPromotion promotion, List<SfUserPromotionRule> ruleList, List<SfUserPromotionGift> promotionGiftList, HttpServletRequest request) {
        insertOrUpdate(promotion, request);
        setPromotionId(promotion.getId(), ruleList, promotionGiftList);
        Collection<Integer> removeRuleIds = sfUserPromotionRuleService.removeInvalidRules(promotion.getId(), ruleList);
        sfUserPromotionGiftService.remvoeByRuleIds(removeRuleIds);
        updateRules(ruleList, promotionGiftList);
    }

    public SfUserPromotion insertOrUpdate(SfUserPromotion promotion, HttpServletRequest request) {
        if(promotion.getId() == null) {
            init4insert(promotion, request);
            sfUserPromotionMapper.insert(promotion);
        } else {
            sfUserPromotionMapper.updateByPrimaryKey(promotion);
        }

        return promotion;
    }

    private void init4insert(SfUserPromotion promotion, HttpServletRequest request) {
        if(promotion.getId() != null) {
            return;
        }

        // 设置 id
        Integer maxId = sfUserPromotionMapper.maxId();
        if(maxId == null) {
            maxId = 0;
        }
        promotion.setId(maxId+1);

        promotion.setCreateTime(new Date()); // 创建时间

        promotion.setType(0);    // 满赠

        promotion.setCode(PromotionMakeUtils.makeCode()); // 活动编码

        // 设置创建者
        Object obj = request.getSession().getAttribute("pbUser");
        PbUser pbUser = (PbUser) obj;
        promotion.setCreateMan(pbUser.getId());
    }

    public void updateRules(List<SfUserPromotionRule> ruleList, List<SfUserPromotionGift> promotionGifts) {

        for(int i=0; i<ruleList.size(); i++) {
            SfUserPromotionRule rule = ruleList.get(i);
            SfUserPromotionGift promotionGift = promotionGifts.get(i);

            sfUserPromotionRuleService.insertOrUpdate(rule);

            promotionGift.setPromoRuleId(rule.getId());
            sfUserPromotionGiftService.insertOrUpdate(promotionGift);
        }

    }

    public Map<String,Object> getPromotion(Integer promotionId) {
        SfUserPromotion promotion = sfUserPromotionMapper.selectByPrimaryKey(promotionId);
        List<SfUserPromotionRule> rules = sfUserPromotionRuleMapper.getPromoRuleByPromoId(promotionId);
        List<SfUserPromotionGift> promotionGifts = sfUserPromotionGiftMapper.getPromoGiftByPromoId(promotionId);

        Map<Integer, SfUserPromotionGift> ruleIdd2PromoGift = new HashMap<>();
        for(SfUserPromotionGift promotionGift : promotionGifts) {
            ruleIdd2PromoGift.put(promotionGift.getPromoRuleId(), promotionGift);
        }

        List ruleWraps = new LinkedList<>();

        for(SfUserPromotionRule rule : rules) {
            SfUserPromotionGift promotionGift =ruleIdd2PromoGift.get(rule.getId());
            Map ruleWrap = new HashMap<>();
            ruleWrap.put("rule", rule);
            ruleWrap.put("promotionGift", promotionGift);
            ruleWraps.add(ruleWrap);
        }

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("promotion", promotion);
        dataMap.put("ruleWraps", ruleWraps);

        return dataMap;
    }

    public List<SfUserPromotion> listByCondition(Integer pageNumber, Integer pageSize, Map<String,Object> conditionMap) {
        PageHelper.startPage(pageNumber, pageSize, "create_time desc");
        return sfUserPromotionMapper.selectByCondition(conditionMap);
    }

    public void setPromotionId(Integer promotionId, List<SfUserPromotionRule> rules, List<SfUserPromotionGift> promotionGifts) {
        if(rules != null) {
            for(SfUserPromotionRule rule : rules) {
                rule.setPromoId(promotionId);
            }
        }

        if(promotionGifts != null) {
            for(SfUserPromotionGift promotionGift : promotionGifts) {
                promotionGift.setPromoId(promotionId);
            }
        }
    }

}
