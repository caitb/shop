package com.masiis.shop.admin.service.promotion;

import com.masiis.shop.dao.mall.promotion.SfUserPromotionGiftMapper;
import com.masiis.shop.dao.po.SfUserPromotion;
import com.masiis.shop.dao.po.SfUserPromotionGift;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 活动规则—奖品　service
 */
@Service
public class SfUserPromotionGiftService {

    @Resource
    private SfUserPromotionGiftMapper sfUserPromotionGiftMapper;

    public void insertOrUpdate(SfUserPromotionGift promotionGift) {
        if(promotionGift.getId() == null) {
            promotionGift.setPromoQuantity(0);
            sfUserPromotionGiftMapper.insert(promotionGift);
        } else {
            sfUserPromotionGiftMapper.updateByPrimaryKey(promotionGift);
        }

    }

    public void remvoeByRuleIds(Collection<Integer> ruleIdList) {
        for(Integer ruleId : ruleIdList) {
            sfUserPromotionGiftMapper.deleteByRuleId(ruleId);
        }
    }

    public List<SfUserPromotionGift> createPromotionGiftList(Integer[] giftValueArray, Integer[] quantityArray, Integer[] upperQuantityArray, Integer[] promotionGiftIdArray) {
        List<SfUserPromotionGift> promotionGiftList = new LinkedList<>();

        for(int i=0; i<giftValueArray.length; i++) {
            SfUserPromotionGift promotionGift = new SfUserPromotionGift();
            promotionGift.setType(0);   // 0:奖品
            promotionGift.setGiftValue(giftValueArray[i]);
            promotionGift.setQuantity(quantityArray[i]);
            promotionGift.setUpperQuantity(upperQuantityArray[i]);

            if(promotionGiftIdArray != null && i<promotionGiftIdArray.length) {
                promotionGift.setId(promotionGiftIdArray[i]);
            }
            promotionGiftList.add(promotionGift);
        }

        return promotionGiftList;
    }

}
