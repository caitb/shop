package com.masiis.shop.web.promotion.cpromotion.service.gorder;

import com.masiis.shop.dao.beans.promotion.PromotionGiftInfo;
import com.masiis.shop.dao.mall.promotion.SfGorderItemMapper;
import com.masiis.shop.dao.po.SfGorderItem;
import com.masiis.shop.web.promotion.cpromotion.service.guser.SfUserPromotionGiftService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * C端用户奖励订单奖励明细表 service
 */
@Service
public class SfGorderItemService {

    @Resource
    private SfGorderItemMapper sfGorderItemMapper;

    @Resource
    private SfUserPromotionGiftService promotionGiftService;

    public void addGorDerItem(Long gorderId,Integer gorderType,Integer promoId,Integer promoRuleId){
        List<PromotionGiftInfo> promotionGiftInfos =  promotionGiftService.getPromoGiftInfoByPromoIdAndRuleId(promoId,promoRuleId,false);
        for (PromotionGiftInfo giftInfo:promotionGiftInfos){
            SfGorderItem sfGorderItem = new SfGorderItem();
            sfGorderItem.setCreateTime(new Date());
            sfGorderItem.setSfGorderId(gorderId);
            sfGorderItem.setGiftId(giftInfo.getPromoGiftId());
            //sfGorderItem.setGiftName(giftInfo.getSkuName());
            sfGorderItem.setUnitPrice(BigDecimal.ZERO);
            //sfGorderItem.setQuantity(giftInfo.getSkuQuantity());
            sfGorderItem.setTotalPrice(BigDecimal.ZERO);
            sfGorderItem.setRemark("领取奖励插入订单明细");
            sfGorderItemMapper.insert(sfGorderItem);
        }
    }
}
