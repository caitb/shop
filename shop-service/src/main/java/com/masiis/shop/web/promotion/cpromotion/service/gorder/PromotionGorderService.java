package com.masiis.shop.web.promotion.cpromotion.service.gorder;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;

import com.masiis.shop.common.enums.mall.SfGOrderPayStatusEnum;
import com.masiis.shop.dao.beans.promotion.PromotionGiftInfo;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.ComUserAddress;
import com.masiis.shop.web.common.service.UserAddressService;
import com.masiis.shop.web.promotion.cpromotion.service.guser.SfUserPromotionGiftService;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 活动订单
 */
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
        List<PromotionGiftInfo> promotionGiftInfos =  promotionGiftService.getPromoGiftInfoByPromoIdAndRuleId(promoId,promoRuleId,true);
        map.put("address",comUserAddress);
        map.put("gift",promotionGiftInfos);
        return map;
    }

    public void receiveReward(ComUser comUser,Long addressId, Integer promoId, Integer promoRuleId,Integer personType){
        //添加订单
        Long gorderId = gorderService.addGorder(comUser,promoId,promoRuleId,personType);
        //添加订单item
        gorderItemService.addGorDerItem(gorderId,personType,promoId,promoRuleId);
        //添加订单操作日志
        gorderOperationLogService.addGorderOperationLog(gorderId,"add",null, SfGOrderPayStatusEnum.ORDER_PAID.getCode(),"领取奖励插入订单操作");
        //添加地址
        //插入用户活动参与记录表
        //修改活动奖励表中的已发奖励数量
    }


}
