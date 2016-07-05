package com.masiis.shop.web.promotion.cpromotion.service.gorder;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.beans.promotion.PromotionGiftInfo;
import com.masiis.shop.dao.mall.promotion.SfGorderItemMapper;
import com.masiis.shop.dao.po.SfGorderItem;
import com.masiis.shop.web.promotion.cpromotion.service.guser.SfUserPromotionGiftService;
import org.springframework.cglib.beans.BulkBeanException;
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

    private Log log = LogFactory.getLog(this.getClass());

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
            sfGorderItem.setGiftName(giftInfo.getGiftName());
            sfGorderItem.setUnitPrice(BigDecimal.ZERO);
            sfGorderItem.setQuantity(giftInfo.getGiftQuantity());
            sfGorderItem.setTotalPrice(BigDecimal.ZERO);
            sfGorderItem.setRemark("领取奖励插入订单明细");
            int i = sfGorderItemMapper.insert(sfGorderItem);
            if (i!=1){
                log.info("---领取奖励插入订单明细失败----");
                throw new BusinessException("---领取奖励插入订单明细失败----");
            }
        }
    }
}
