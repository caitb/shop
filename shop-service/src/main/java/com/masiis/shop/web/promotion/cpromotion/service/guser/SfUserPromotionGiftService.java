package com.masiis.shop.web.promotion.cpromotion.service.guser;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.common.util.OSSObjectUtils;
import com.masiis.shop.dao.beans.promotion.PromotionGiftInfo;
import com.masiis.shop.dao.mall.promotion.SfUserPromotionGiftMapper;
import com.masiis.shop.dao.po.ComGift;
import com.masiis.shop.dao.po.ComSku;
import com.masiis.shop.dao.po.SfUserPromotionGift;
import com.masiis.shop.web.common.service.ComGiftService;
import com.masiis.shop.web.common.service.SkuService;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 商城用户活动奖励表service
 */
@Service
@Transactional
public class SfUserPromotionGiftService {

    private Log log = LogFactory.getLog(this.getClass());

    @Resource
    private SfUserPromotionGiftMapper sfUserPromotionGiftMapper;
    @Resource
    private ComGiftService comGiftService;

    /**
     *  修改商城用户活动奖励表 的 已发放奖励数量
     * @param quantity
     * @param promoId
     * @param promoRuleId
     * @param giftValue
     * @return
     */
    public int addPromoQuantity(Integer quantity,Integer promoId,Integer promoRuleId,Integer giftValue){
        SfUserPromotionGift  gift = getPromoGiftByPromoIdAndRuleIdAndGiftValue(promoId,promoRuleId,giftValue);
        if (gift!=null){
            gift.setPromoQuantity(gift.getPromoQuantity()+quantity);
            gift.setRemark("修改商城用户活动奖励表的已发放奖励数量");
            return sfUserPromotionGiftMapper.updateByPrimaryKey(gift);
        }else{
            return 0;
        }
    }


    public SfUserPromotionGift getPromoGiftByPromoIdAndRuleIdAndGiftValue(Integer promoId,Integer promoRuleId,Integer giftValue){
        return sfUserPromotionGiftMapper.getPromoGiftByPromoIdAndRuleIdAndGiftValue(promoId,promoRuleId,giftValue);
    }

    public List<SfUserPromotionGift> getPromoGiftByPromoIdAndRuleId(Integer promoId,Integer promoRuleId){
        return sfUserPromotionGiftMapper.getPromoGiftByPromoIdAndRuleId(promoId,promoRuleId);
    }

    public List<PromotionGiftInfo>  getPromoGiftInfosByPromoIdAndRuleId(Integer promoId,Integer promoRuleId,Boolean isGetImage){
        List<SfUserPromotionGift> promoGifts =  getPromoGiftByPromoIdAndRuleId(promoId,promoRuleId);
        List<PromotionGiftInfo> detailInfos = new ArrayList<>();
        for (SfUserPromotionGift promoGift : promoGifts){
            PromotionGiftInfo giftInfo = getPromoGiftInfoByPromoIdAndRuleId(promoId,promoRuleId,isGetImage,null,promoGift);
            detailInfos.add(giftInfo);
        }
        return detailInfos;
    }

    public PromotionGiftInfo getPromoGiftInfoByPromoIdAndRuleId(Integer promoId,Integer promoRuleId,Boolean isGetImage,Integer giftId,SfUserPromotionGift promoGift){
        if (giftId!=null){
            promoGift = getPromoGiftByPromoIdAndRuleIdAndGiftValue(promoId,promoRuleId,giftId);
        }
        log.info("获得奖品信息-----奖品id-----"+giftId);
        ComGift comGift = comGiftService.getComGiftById(giftId);
        PromotionGiftInfo giftInfo = new PromotionGiftInfo();
        if (comGift!=null&&comGift.getStatus()==1){
            giftInfo.setPromoGiftId(giftId);
            giftInfo.setGiftId(comGift.getId());
            giftInfo.setGiftName(comGift.getName());
            giftInfo.setSendedQuantity(promoGift.getPromoQuantity());
            giftInfo.setMaxQuantity(promoGift.getUpperQuantity());
            giftInfo.setGiftQuantity(promoGift.getQuantity());
            if (isGetImage){
                giftInfo.setGiftImageUrl(OSSObjectUtils.OSS_GIFT_URL + comGift.getImgUrl());
            }
            if (promoGift.getUpperQuantity()-promoGift.getPromoQuantity()<promoGift.getQuantity()){
                giftInfo.setIsEnoughQuantity(false);
            }else{
                giftInfo.setIsEnoughQuantity(true);
            }
        }else{
            log.info("奖品为null或者奖品失效");
        }
        return giftInfo;
    }
}
