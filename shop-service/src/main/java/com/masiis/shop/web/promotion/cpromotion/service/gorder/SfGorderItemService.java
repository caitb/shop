package com.masiis.shop.web.promotion.cpromotion.service.gorder;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.common.enums.promotion.SfGorderTypeEnum;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.beans.promotion.PromotionGiftInfo;
import com.masiis.shop.dao.beans.promotion.TurnTableGiftInfo;
import com.masiis.shop.dao.mall.promotion.SfGorderItemMapper;
import com.masiis.shop.dao.po.SfGorderItem;
import com.masiis.shop.dao.po.SfTurnTableGift;
import com.masiis.shop.dao.po.SfUserTurnTable;
import com.masiis.shop.web.promotion.cpromotion.service.guser.SfUserPromotionGiftService;
import com.masiis.shop.web.promotion.cpromotion.service.guser.SfUserTurnTableService;
import org.springframework.cglib.beans.BulkBeanException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * C端用户奖励订单奖励明细表 service
 */
@Service
@Transactional
public class SfGorderItemService {

    private Log log = LogFactory.getLog(this.getClass());

    @Resource
    private SfGorderItemMapper sfGorderItemMapper;

    @Resource
    private SfUserPromotionGiftService promotionGiftService;
    @Resource
    private SfTurnTableGiftService turnTableGiftService;


    public Object addGorDerItem(Long gorderId,SfGorderTypeEnum gorderTypeEnum,Integer promoId,Integer promoRuleId){
        switch (gorderTypeEnum){
            case ORDER_PROMOTION :
                return addGorDerItemForPromotion(gorderId,gorderTypeEnum,promoId,promoRuleId);
            case ORDER_TURN_TABLE:
                addGorDerItemForTurnTable(gorderId,promoId,promoRuleId);
                break;
            default:
                return null;
        }
        return null;
    }

    /**
     * 大转盘抽奖插入订单明细
     * @param gorderId
     * @param turnTableId
     * @param giftId
     */
    public void addGorDerItemForTurnTable(Long gorderId,Integer turnTableId,Integer giftId){
        TurnTableGiftInfo turnTableGiftInfo = turnTableGiftService.getTurnTableGiftInfo(turnTableId,giftId);
        SfGorderItem sfGorderItem = new SfGorderItem();
        if (turnTableGiftInfo!=null){
            sfGorderItem.setGiftName(turnTableGiftInfo.getGiftName());
            sfGorderItem.setQuantity(turnTableGiftInfo.getQuantity());
        }
        sfGorderItem.setCreateTime(new Date());
        sfGorderItem.setSfGorderId(gorderId);
        sfGorderItem.setGiftId(giftId);
        sfGorderItem.setUnitPrice(BigDecimal.ZERO);
        sfGorderItem.setTotalPrice(BigDecimal.ZERO);
        sfGorderItem.setRemark("大转盘抽奖插入订单明细");
        int i = sfGorderItemMapper.insert(sfGorderItem);
        if (i!=1){
            log.info("---大转盘抽奖插入订单明细失败----");
            throw new BusinessException("---大转盘抽奖插入订单明细失败----");
        }
    }

    /**
     * 活动领取奖励插入订单明细
     * @param gorderId
     * @param gorderTypeEnum
     * @param promoId
     * @param promoRuleId
     * @return
     */
    public List<PromotionGiftInfo>  addGorDerItemForPromotion(Long gorderId,SfGorderTypeEnum gorderTypeEnum,Integer promoId,Integer promoRuleId){
        List<PromotionGiftInfo> promotionGiftInfos =  promotionGiftService.getPromoGiftInfosByPromoIdAndRuleId(promoId,promoRuleId,false);
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
        return promotionGiftInfos;
    }
}
