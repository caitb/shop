package com.masiis.shop.web.promotion.cpromotion.service.guser;

import com.masiis.shop.common.enums.promotion.SfUserTurnTableRecordStatusEnum;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.dao.beans.promotion.UserTurnTableRecordInfo;
import com.masiis.shop.dao.mall.promotion.SfUserTurnTableRecordMapper;
import com.masiis.shop.dao.po.ComGift;
import com.masiis.shop.dao.po.SfUserTurnTableRecord;
import com.masiis.shop.web.common.service.ComGiftService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户转盘中奖记录service
 */
@Service
public class SfUserTurnTableRecordService {

    @Resource
    private SfUserTurnTableRecordMapper userTurnTableRecordMapper;
    @Resource
    private ComGiftService comGiftService;


    public SfUserTurnTableRecord getRecordByUserIdAndTurnTableIdAndGiftId(Long userId,Integer turnTableId,Integer giftId ){
        return userTurnTableRecordMapper.getRecordByUserIdAndTurnTableIdAndGiftId(userId,turnTableId,giftId);
    }

    /**
     * 更新大转盘中奖纪录状态和订单id
     * @param userId
     * @param turnTableId
     * @param giftId
     * @param status
     * @return
     */
    public void updateRecordStatusAndGorderId(Long userId,Integer turnTableId,Integer giftId ,int status,Long gorderId){
        SfUserTurnTableRecord userTurnTableRecord = getRecordByUserIdAndTurnTableIdAndGiftId(userId,turnTableId,giftId);
        if (userTurnTableRecord!=null){
            userTurnTableRecord.setStatus(status);
            if (gorderId!=null){
                userTurnTableRecord.setGorderId(gorderId);
            }
            userTurnTableRecord.setUpdateTime(new Date());
            userTurnTableRecord.setRemark("大转盘记录更新状态");
            int i =  userTurnTableRecordMapper.updateByPrimaryKey(userTurnTableRecord);
            if (i!=1){
                throw new BusinessException("大转盘记录更新状态---更新失败");
            }
        }else{
            throw new BusinessException("大转盘记录更新状态,查询实体失败");
        }
    }

    /**
     * 获取用户的中奖纪录
     * @param userId
     * @return
     */
    public List<UserTurnTableRecordInfo> getRecordInfoByUserId(Long userId){
        List<SfUserTurnTableRecord>  records =  userTurnTableRecordMapper.getRecordInfoByUserId(userId);
        List<UserTurnTableRecordInfo> recordInfoList = new ArrayList<UserTurnTableRecordInfo>();
        for (SfUserTurnTableRecord record:records){
            UserTurnTableRecordInfo recordInfo = new UserTurnTableRecordInfo();
            recordInfo.setStatus(record.getStatus());
            recordInfo.setCreateTimeString(DateUtil.Date2String(record.getCreateTime(),DateUtil.CHINESE_YEAR_MONTH_DATE_FMT));
            if (record.getStatus()==SfUserTurnTableRecordStatusEnum.GIFT_NOT_RECEIVE.getCode()){
                recordInfo.setStatusName("未领取");
            }else if (record.getStatus()==SfUserTurnTableRecordStatusEnum.GIFT_RECEIVED.getCode()){
                recordInfo.setStatusName("已领领取");
            }
            ComGift comGift = comGiftService.getComGiftById(record.getGiftId());
            if (comGift!=null){
                recordInfo.setTurnTableGiftName(comGift.getName());
            }
            recordInfoList.add(recordInfo);
        }
        return recordInfoList;
    }
}
