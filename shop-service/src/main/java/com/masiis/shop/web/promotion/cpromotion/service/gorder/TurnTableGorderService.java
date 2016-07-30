package com.masiis.shop.web.promotion.cpromotion.service.gorder;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.common.enums.promotion.SfGOrderPayStatusEnum;
import com.masiis.shop.common.enums.promotion.SfGorderTypeEnum;
import com.masiis.shop.common.enums.promotion.SfUserTurnTableRecordStatusEnum;
import com.masiis.shop.common.enums.promotion.SfUserTurnTableTimesTypeEnum;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.beans.promotion.TurnTableGiftInfo;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.common.service.UserAddressService;
import com.masiis.shop.web.promotion.cpromotion.service.guser.SfUserTurnTableItemService;
import com.masiis.shop.web.promotion.cpromotion.service.guser.SfUserTurnTableRecordService;
import com.masiis.shop.web.promotion.cpromotion.service.guser.SfUserTurnTableService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hzz on 2016/7/29.
 */
@Service
public class TurnTableGorderService {

    private Log log = LogFactory.getLog(this.getClass());

    @Resource
    private UserAddressService userAddressService;
    @Resource
    private SfTurnTableGiftService sfTurnTableGiftService;
    @Resource
    private SfUserTurnTableService sfUserTurnTableService;
    @Resource
    private SfGorderService gorderService;
    @Resource
    private SfGorderItemService gorderItemService;
    @Resource
    private SfGorderOperationLogService gorderOperationLogService;
    @Resource
    private SfGorderConsigneeService gorderConsigneeService;
    @Resource
    private SfUserTurnTableItemService sfUserTurnTableItemService;
    @Resource
    private SfUserTurnTableRecordService userTurnTableRecordService;

    /**
     * 获取抽中的奖品的信息
     * @param userId
     * @param selectedAddressId
     * @param turnTableId
     * @param giftId
     * @return
     */
    public Map<String,Object> getTurnTableGiftInfo(Long userId, Long selectedAddressId,Integer turnTableId,Integer giftId){
        Map<String,Object> map = new HashMap<String,Object>();
        ComUserAddress comUserAddress = userAddressService.getOrderAddress( selectedAddressId, userId);
        TurnTableGiftInfo turnTableGiftInfo = sfTurnTableGiftService.getTurnTableGiftInfo(turnTableId,giftId);
        map.put("address",comUserAddress);
        map.put("turnTableGiftInfo",turnTableGiftInfo);
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRED,readOnly = false)
    public Integer receiveGift(ComUser comUser, Long addressId, Integer turnTableId, Integer giftId,Integer turnTableRuleId){
        log.info("receiveGift入口参数------userId---"+comUser.getId()+"-----address------"+addressId+"-----turnTableId-----"+turnTableId);
        log.info("giftId------"+giftId+"-------------turnTableRuleId----"+turnTableRuleId);
        //判断是否满足条件领取
        log.info("判断奖品还有没有--------start");
        Integer i = isMayReceiveGift(comUser,turnTableId,giftId);
        if (i<3){
            return i;
        }
        log.info("判断奖品还有没有--------end");
        //领取
        log.info("判断领取次数还有没有--------start");
        SfGorderItem sfGorderItem = receiveGiftAddGorder(comUser,addressId,turnTableId,giftId);
        log.info("判断领取次数还有没有--------end");
        //领取后的操作
        log.info("领取后的操作--------start");
        receiveGiftAfterOpertion(1,comUser.getId(),turnTableId,turnTableRuleId,giftId,sfGorderItem.getSfGorderId());
        log.info("领取后的操作--------end");
        return null;
    }

    /**
     *  判断是否满足条件领取
     * @param comUser
     * @param turnTableId
     * @param giftId
     * @return
     */
    private Integer isMayReceiveGift(ComUser comUser,Integer turnTableId, Integer giftId){
        //判断奖品还有没有
        SfTurnTableGift sfTurnTableGift = sfTurnTableGiftService.getSfTurnTableGift(turnTableId,giftId);
        if (sfTurnTableGift!=null&&sfTurnTableGift.getToatalQuantity().equals(0)){
            if (sfTurnTableGift.getToatalQuantity().equals(0)){
                //奖品数量没有了
                return 1;
            }
        }else{
            throw new BusinessException("----奖品存在------");
        }
        //判断领取次数还有没有
        SfUserTurnTable sfUserTurnTable = sfUserTurnTableService.getSfUserTurnTable(comUser.getId(),turnTableId);
        if (sfUserTurnTable!=null){
            if (sfUserTurnTable.getNotUsedTimes().equals(0)){
                //已经没有可抽奖的次数
                return 2;
            }
        }else{
            throw new BusinessException("-----用户转盘领取信息不存在-----------");
        }
        return 0;
    }

    /**
     * 领取奖品插入订单
     * @param comUser
     * @param turnTableId
     * @param giftId
     * @param addressId
     */
    private SfGorderItem receiveGiftAddGorder(ComUser comUser,Long addressId,Integer turnTableId, Integer giftId){
        //插入订单
        SfGorder sfGorder = gorderService.addGorder(comUser,turnTableId,giftId, SfGorderTypeEnum.ORDER_TURN_TABLE);
        //插入订单明细
        SfGorderItem sfGorderItem = (SfGorderItem)gorderItemService.addGorDerItem(sfGorder.getId(),SfGorderTypeEnum.ORDER_PROMOTION,turnTableId,giftId);
        //插入订单操作日志
        gorderOperationLogService.addGorderOperationLog(comUser.getId(),sfGorder.getId(),"add",null, SfGOrderPayStatusEnum.ORDER_PAID.getCode(),"大转盘领取奖品插入订单操作");
        //插入地址
        log.info("添加地址-------------start");
        int i = gorderConsigneeService.addGorderConsignee(sfGorder.getId(),addressId);
        if (i==1){
            log.info("添加地址------成功-------end");
        }else{
            log.info("添加地址------失败-------end");
            throw new BusinessException("添加地址------失败");
        }
        if (sfGorderItem!=null){
            return sfGorderItem;
        }else {
            throw new BusinessException("大转盘领取奖励插入订单明细失败");
        }
    }

    /**
     * 领取奖品后更新用户转盘的次数和增加领取奖励记录
     * @param changeTimes
     * @param userId
     * @param turnTableId
     * @param turnTableRuleId
     * @param gorderId
     */
    private void receiveGiftAfterOpertion(Integer changeTimes,Long userId,Integer turnTableId,Integer turnTableRuleId,Integer giftId,Long gorderId){
        //奖品奖励数量减少
        //用户转盘增加已抽奖次数，减少未抽奖次数
        sfUserTurnTableService.reduceTimesOrAddTimes(SfUserTurnTableTimesTypeEnum.REDUCE_TIMES.getCode(),changeTimes,userId,turnTableId);
        //增加用户转盘具体信息:减少次数
        sfUserTurnTableItemService.insert(SfUserTurnTableTimesTypeEnum.REDUCE_TIMES.getCode(),changeTimes,turnTableId,turnTableRuleId);
        //增加用户领取奖励记录
        userTurnTableRecordService.updateRecordStatusAndGorderId(userId,turnTableId,giftId, SfUserTurnTableRecordStatusEnum.GIFT_RECEIVED.getCode(),gorderId);
    }
}
