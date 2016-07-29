package com.masiis.shop.web.promotion.cpromotion.service.gorder;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.dao.beans.promotion.TurnTableGiftInfo;
import com.masiis.shop.dao.mall.promotion.SfTurnTableGiftMapper;
import com.masiis.shop.dao.po.ComGift;
import com.masiis.shop.dao.po.SfTurnTableGift;
import com.masiis.shop.web.common.service.ComGiftService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *  转盘奖品表service
 */
@Service
public class SfTurnTableGiftService {

    private Log log = LogFactory.getLog(this.getClass());

    @Resource
    private SfTurnTableGiftMapper turnTableGiftMapper;
    @Resource
    private ComGiftService comGiftService;

    /**
     * 获得抽中的奖品信息
     * @param turnTableId   转盘id
     * @param giftId        奖品id
     * @return
     */
    public TurnTableGiftInfo getTurnTableGiftInfo(Integer turnTableId,Integer giftId){
        SfTurnTableGift turnTableGift =  turnTableGiftMapper.getTurnTableGiftInfo(turnTableId,giftId);
        TurnTableGiftInfo turnTableGiftInfo = null;
        if (turnTableGift!=null){
            turnTableGiftInfo = new TurnTableGiftInfo();
            log.info("获得转盘奖品信息-----奖品id-----"+turnTableGift.getGiftId());
            ComGift comGift = comGiftService.getComGiftById(turnTableGift.getGiftId());
            if (comGift!=null){
                turnTableGiftInfo.setGiftName(comGift.getName());
                turnTableGiftInfo.setQuantity(turnTableGift.getQuantity());
                turnTableGiftInfo.setQuantity(turnTableGift.getQuantity());
                turnTableGiftInfo.setImgUrl(comGift.getImgUrl());
            }
        }
        return turnTableGiftInfo;
    }

    public SfTurnTableGift getSfTurnTableGift(Integer turnTableId,Integer giftId){
        return turnTableGiftMapper.getTurnTableGiftInfo(turnTableId,giftId);
    }
}
