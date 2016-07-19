package com.masiis.shop.web.common.service;

import com.masiis.shop.dao.beans.promotion.PromotionGiftInfo;
import com.masiis.shop.dao.platform.gift.ComGiftMapper;
import com.masiis.shop.dao.po.ComGift;
import com.masiis.shop.dao.po.SfUserPromotionGift;
import com.masiis.shop.web.promotion.cpromotion.service.guser.SfUserPromotionGiftService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by hzz on 2016/7/5.
 */
@Service
public class ComGiftService {

    @Resource
    private ComGiftMapper comGiftMapper;

    @Resource
    private SfUserPromotionGiftService promotionGiftService;

    public ComGift getComGiftById(Integer id){
        return comGiftMapper.selectByPrimaryKey(id);
    }

    public PromotionGiftInfo getPromoGiftInfoByPromoIdAndRuleId(Integer promoId,Integer promoRuleId,Integer giftId){
       return promotionGiftService.getPromoGiftInfoByPromoIdAndRuleId(promoId,promoRuleId,true,giftId,null);
    }

}
