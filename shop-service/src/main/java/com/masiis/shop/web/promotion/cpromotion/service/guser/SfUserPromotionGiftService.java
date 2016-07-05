package com.masiis.shop.web.promotion.cpromotion.service.guser;

import com.masiis.shop.dao.beans.promotion.PromotionGiftInfo;
import com.masiis.shop.dao.mall.promotion.SfUserPromotionGiftMapper;
import com.masiis.shop.dao.po.ComSku;
import com.masiis.shop.dao.po.SfUserPromotionGift;
import com.masiis.shop.web.common.service.SkuService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 商城用户活动奖励表service
 */
@Service
public class SfUserPromotionGiftService {

    @Resource
    private SfUserPromotionGiftMapper sfUserPromotionGiftMapper;
    @Resource
    private SkuService skuService;

    public List<SfUserPromotionGift> getPromoGiftByPromoIdAndRuleId(Integer promoId,Integer promoRuleId){
        return sfUserPromotionGiftMapper.getPromoGiftByPromoIdAndRuleId(promoId,promoRuleId);
    }

    public List<PromotionGiftInfo>  getPromoGiftInfoByPromoIdAndRuleId(Integer promoId,Integer promoRuleId){
        List<SfUserPromotionGift> promoGifts =  getPromoGiftByPromoIdAndRuleId(promoId,promoRuleId);
        List<PromotionGiftInfo> detailInfos = new ArrayList<>();
        for (SfUserPromotionGift promoGift : promoGifts){
            ComSku comsku = skuService.getSkuById(promoGift.getGiftValue());
            PromotionGiftInfo giftInfo = new PromotionGiftInfo();
            if (comsku!=null){
                giftInfo.setPromoGiftId(promoGift.getId());
                giftInfo.setSkuId(comsku.getId());
                giftInfo.setSkuName(comsku.getName());
                giftInfo.setSkuQuantity(promoGift.getQuantity());
            }
            detailInfos.add(giftInfo);
        }
        return detailInfos;
    }
}
