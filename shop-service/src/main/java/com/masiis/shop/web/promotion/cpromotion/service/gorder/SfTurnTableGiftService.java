package com.masiis.shop.web.promotion.cpromotion.service.gorder;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.OSSObjectUtils;
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

    public SfTurnTableGift selectByPrimaryKey(Integer id){
       return turnTableGiftMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询转盘下的所有奖品信息
     * @param turnTableId
     * @return
     */
    public List<TurnTableGiftInfo> getTurnTableGiftsByTableId(Integer turnTableId){
        List<SfTurnTableGift> turnTableGifts = turnTableGiftMapper.getTableGiftsByTurnTableIdAndSortAsc(turnTableId);
        List<TurnTableGiftInfo> turnTableGiftInfos = new ArrayList<TurnTableGiftInfo>();
        for (SfTurnTableGift turnTableGift: turnTableGifts){
            TurnTableGiftInfo turnTableGiftInfo = getTurnTableGiftInfo(turnTableId,turnTableGift.getGiftId(),turnTableGift.getSort());
            turnTableGiftInfos.add(turnTableGiftInfo);
        }
        return turnTableGiftInfos;
    }


    /**
     * 获得大转盘的奖品信息
     * @param turnTableId   转盘id
     * @param giftId        奖品id
     * @return
     */
    public TurnTableGiftInfo getTurnTableGiftInfo(Integer turnTableId,Integer giftId,Integer sort){
        SfTurnTableGift turnTableGift =  turnTableGiftMapper.getTurnTableGiftInfoByTableIdAndGiftIdAndSort(turnTableId,giftId,sort);
        TurnTableGiftInfo turnTableGiftInfo = null;
        if (turnTableGift!=null){
            return generateGiftInfo(turnTableGift);
        }
        return turnTableGiftInfo;
    }


    /**
     * 获得抽中的奖品信息
     * @param turnTableId   转盘id
     * @param giftId        奖品id
     * @return
     */
    public TurnTableGiftInfo getTurnTableGiftInfo(Integer turnTableId,Integer giftId){
        List<SfTurnTableGift> turnTableGifts =  turnTableGiftMapper.getTurnTableGiftInfo(turnTableId,giftId);
        TurnTableGiftInfo turnTableGiftInfo = null;
        if (turnTableGifts!=null&&turnTableGifts.size()!=0){
            return generateGiftInfo(turnTableGifts.get(0));
        }
        return turnTableGiftInfo;
    }

    private TurnTableGiftInfo generateGiftInfo(SfTurnTableGift turnTableGift ){
        TurnTableGiftInfo turnTableGiftInfo = new TurnTableGiftInfo();
        log.info("获得转盘奖品信息-----奖品id-----"+turnTableGift.getGiftId());
        ComGift comGift = comGiftService.getComGiftById(turnTableGift.getGiftId());
        if (comGift!=null){
            turnTableGiftInfo.setTurnTableGiftId(turnTableGift.getId());
            turnTableGiftInfo.setTurnTableId(turnTableGift.getTurnTableId());
            turnTableGiftInfo.setGiftId(turnTableGift.getGiftId());
            turnTableGiftInfo.setGiftName(comGift.getName());
            turnTableGiftInfo.setQuantity(turnTableGift.getQuantity());
            turnTableGiftInfo.setImgUrl(OSSObjectUtils.OSS_GIFT_URL + comGift.getImgUrl());
            turnTableGiftInfo.setSort(turnTableGift.getSort());
            turnTableGiftInfo.setProbability(turnTableGift.getProbability());
            turnTableGiftInfo.setIsGift(comGift.getIsGift());
            turnTableGiftInfo.setToatalQuantity(turnTableGift.getToatalQuantity());
            turnTableGiftInfo.setGiftedQuantity(turnTableGift.getGiftedQuantity());
        }
        return turnTableGiftInfo;
    }
    /**
     * 更新大转盘的已中奖数量
     * @param turnTableGiftId
     */
    public int updateGiftedQuantity(Integer turnTableGiftId){
        log.info("更新大转盘的已中奖数量------id----"+turnTableGiftId);
        SfTurnTableGift turnTableGift =  selectByPrimaryKey(turnTableGiftId);
        int i = 0;
        if (turnTableGift!=null){
            turnTableGift.setGiftedQuantity(turnTableGift.getGiftedQuantity()+turnTableGift.getQuantity());
            i = turnTableGiftMapper.updateGiftedQuantity(turnTableGift.getId(),turnTableGift.getGiftedQuantity());
        }else{
            throw new BusinessException("更新大转盘奖品已中奖数量失败----转盘奖品不存在");
        }
        return i;
    }
}
