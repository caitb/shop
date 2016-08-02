package com.masiis.shop.web.promotion.cpromotion.service.gorder;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.beans.promotion.TurnTableGiftInfo;
import com.masiis.shop.dao.mall.promotion.SfTurnTableGiftMapper;
import com.masiis.shop.dao.po.ComGift;
import com.masiis.shop.dao.po.SfTurnTableGift;
import com.masiis.shop.web.common.service.ComGiftService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
     * 查询转盘下的所有奖品信息
     * @param turnTableId
     * @return
     */
    public List<TurnTableGiftInfo> getTurnTableGiftsByTableId(Integer turnTableId){
        List<SfTurnTableGift> turnTableGifts = turnTableGiftMapper.listByTurnTableId(turnTableId);
        List<TurnTableGiftInfo> turnTableGiftInfos = new ArrayList<TurnTableGiftInfo>();
        for (SfTurnTableGift turnTableGift: turnTableGifts){
            TurnTableGiftInfo turnTableGiftInfo = getTurnTableGiftInfo(turnTableId,turnTableGift.getGiftId());
            turnTableGiftInfos.add(turnTableGiftInfo);
        }
        return turnTableGiftInfos;
    }

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
                turnTableGiftInfo.setGiftId(turnTableGift.getGiftId());
                turnTableGiftInfo.setGiftName(comGift.getName());
                turnTableGiftInfo.setQuantity(turnTableGift.getQuantity());
                turnTableGiftInfo.setQuantity(turnTableGift.getQuantity());
                turnTableGiftInfo.setImgUrl(comGift.getImgUrl());
                turnTableGiftInfo.setSort(turnTableGift.getSort());
            }
        }
        return turnTableGiftInfo;
    }

    public SfTurnTableGift getSfTurnTableGift(Integer turnTableId,Integer giftId){
        return turnTableGiftMapper.getTurnTableGiftInfo(turnTableId,giftId);
    }

    /**
     * 更新大转盘的已中奖数量
     * @param turnTableId
     * @param giftId
     */
    public void updateGiftedQuantity(Integer turnTableId,Integer giftId){
        SfTurnTableGift turnTableGift =  getSfTurnTableGift(turnTableId,giftId);
        if (turnTableGift!=null){
            turnTableGift.setGiftedQuantity(turnTableGift.getGiftedQuantity()+turnTableGift.getQuantity());
            int i = turnTableGiftMapper.updateByPrimaryKey(turnTableGift);
            if (i!=1){
                throw new BusinessException("更新大转盘奖品已中奖数量失败");
            }
        }
    }
}
