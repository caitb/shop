package com.masiis.shop.web.promotion.cpromotion.service.gorder;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;

import com.masiis.shop.common.enums.promotion.SfGOrderPayStatusEnum;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.beans.promotion.PromotionGiftInfo;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.common.service.UserAddressService;
import com.masiis.shop.web.promotion.cpromotion.service.guser.SfUserPromotionGiftService;
import com.masiis.shop.web.promotion.cpromotion.service.guser.SfUserPromotionRecordService;
import com.masiis.shop.web.promotion.cpromotion.service.guser.SfUserPromotionRuleService;
import com.masiis.shop.web.promotion.cpromotion.service.guser.SfUserPromotionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 活动订单
 */
@Service
@Transactional
public class PromotionGorderService {

    private Log log = LogFactory.getLog(this.getClass());

    @Resource
    private UserAddressService userAddressService;
    @Resource
    private SfUserPromotionGiftService promotionGiftService;
    @Resource
    private SfGorderService gorderService;
    @Resource
    private SfGorderItemService gorderItemService;
    @Resource
    private SfGorderConsigneeService gorderConsigneeService;
    @Resource
    private SfGorderOperationLogService gorderOperationLogService;
    @Resource
    private SfUserPromotionRecordService recordService;
    @Resource
    private SfUserPromotionRuleService userPromotionRuleService;
    @Resource
    private SfUserPromotionService userPromotionService;





    /**
     *  获得活动订单页面信息
     * @param userId
     * @param selectedAddressId
     * @param promoId
     * @param promoRuleId
     * @return
     */
    public Map<String, Object> getPromotionGorderPageInfo(Long userId, Long selectedAddressId,Integer promoId,Integer promoRuleId){
        Map<String,Object> map = new HashMap<String,Object>();
        ComUserAddress comUserAddress = userAddressService.getOrderAddress( selectedAddressId, userId);
        log.info("获取奖品信息-----活动id----"+promoId+"-----规则id-----"+promoRuleId);
        List<PromotionGiftInfo> promotionGiftInfos =  promotionGiftService.getPromoGiftInfosByPromoIdAndRuleId(promoId,promoRuleId,true);
        map.put("address",comUserAddress);
        map.put("gifts",promotionGiftInfos);
        return map;
    }
    /**
     * 领取奖励
     * @param comUser
     * @param addressId  地址id
     * @param promoId   活动id
     * @param promoRuleId 活动规则id
     */
    public Integer receiveReward(ComUser comUser,Long addressId, Integer promoId, Integer promoRuleId){
        //判断活动是否领取
        log.info("领取奖励service-----------start");
        log.info("service入口参数-------comUser的id-----"+comUser.getId()+"-----addressId----"+addressId+"-----promoId----"+promoId+"------promoRuleId-----"+promoRuleId);
        SfUserPromotionRecord record = recordService.getPromoRecordByUserIdAndPromoIdAndRuleId(comUser.getId(),promoId,promoRuleId);
        if (record!=null){
            //活动已领取
            return 2;
        }
        SfUserPromotion sfUserPromotion = userPromotionService.selectByPrimaryKey(promoId);
        if (sfUserPromotion==null){
            throw new BusinessException("promoId参数不合法,查询活动");
        }
        Boolean bl = userPromotionRuleService.isMayReceiveReward(comUser,promoRuleId,sfUserPromotion.getPersonType());
        if (!bl){
            //粉丝数量未达到不能领取
            return 0;
        }
        //添加订单
        log.info("添加订单-------------start");
        SfGorder sfGorder = gorderService.addGorder(comUser,promoId,promoRuleId);
        log.info("添加订单---------------end");
        //添加订单item
        log.info("添加订单item------start");
        log.info("订单id-------------"+sfGorder.getId());
        List<PromotionGiftInfo> promotionGiftInfos = gorderItemService.addGorDerItem(sfGorder.getId(),sfGorder.getGorderType(),promoId,promoRuleId);
        log.info("添加订单item-------end");
        //添加订单操作日志
        log.info("添加订单操作日志-------start");
        gorderOperationLogService.addGorderOperationLog(comUser.getId(),sfGorder.getId(),"add",null, SfGOrderPayStatusEnum.ORDER_PAID.getCode(),"领取奖励插入订单操作");
        log.info("添加订单操作日志-------end");
        //添加地址
        log.info("添加地址-------------start");
        int i = gorderConsigneeService.addGorderConsignee(sfGorder.getId(),addressId);
        if (i==1){
            log.info("添加地址------成功-------end");
        }else{
            log.info("添加地址------失败-------end");
            throw new BusinessException("添加地址------失败");
        }
        //插入用户活动参与记录表
        log.info("插入用户东东参与记录表-------------start");
        int ii=recordService.addSfUserPromotionRecord(comUser.getId(),promoId,promoRuleId);
        if (ii==1){
            log.info("插入用户东东参与记录表-------成功------end");
        }else{
            log.info("插入用户东东参与记录表------失败--------end");
            throw new BusinessException("插入用户东东参与记录表------失败");
        }
        //修改活动奖励表中的已发奖励数量
        log.info("修改活动奖励表中的已发奖励数量----------start");
        for (PromotionGiftInfo promotionGiftInfo:promotionGiftInfos){
            promotionGiftService.addPromoQuantity(promotionGiftInfo.getGiftQuantity(),promoId,promoRuleId,promotionGiftInfo.getGiftId());
        }
        log.info("修改活动奖励表中的已发奖励数量----------end");
        log.info("领取奖励service-----------end");
        return 1;
    }


}
