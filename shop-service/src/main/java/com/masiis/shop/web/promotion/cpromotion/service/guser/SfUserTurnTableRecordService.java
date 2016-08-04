package com.masiis.shop.web.promotion.cpromotion.service.guser;

import com.masiis.shop.common.enums.promotion.SfUserTurnTableRecordStatusEnum;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.dao.beans.promotion.UserTurnTableRecordInfo;
import com.masiis.shop.dao.mall.promotion.SfUserTurnTableRecordMapper;
import com.masiis.shop.dao.po.ComGift;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.SfTurnTableGift;
import com.masiis.shop.dao.po.SfUserTurnTableRecord;
import com.masiis.shop.web.common.service.ComGiftService;
import com.masiis.shop.web.promotion.cpromotion.service.gorder.SfTurnTableGiftService;
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
    @Resource
    private SfTurnTableGiftService turnTableGiftService;

    public SfUserTurnTableRecord selectByPrimaryKey(Long id){
        return  userTurnTableRecordMapper.selectByPrimaryKey(id);
    }

    public List<SfUserTurnTableRecord> getRecordByTableId(Integer turnTableId){
        return userTurnTableRecordMapper.getRecordByTableId(turnTableId);
    }

    public SfUserTurnTableRecord getRecordByUserIdAndTurnTableIdAndGiftId(Long userId,Integer turnTableId,Integer giftId ){
        return userTurnTableRecordMapper.getRecordByUserIdAndTurnTableIdAndGiftId(userId,turnTableId,giftId);
    }

    /**
     * 用户抽中奖品
     * @param comUser
     * @param turnTableId
     * @param giftId
     * @return
     */
    public Long winGift(ComUser comUser,Integer turnTableId,Integer giftId){
        SfUserTurnTableRecord record = new SfUserTurnTableRecord();
        record.setUserId(comUser.getId());
        record.setTurnTableId(turnTableId);
        record.setGiftId(giftId);
        record.setStatus(SfUserTurnTableRecordStatusEnum.GIFT_NOT_RECEIVE.getCode());
        record.setCreateTime(new Date());
        record.setCreateMan(comUser.getId());
        record.setRemark("用户大转盘抽中奖品未领取");
        int i = userTurnTableRecordMapper.insert(record);
        if (i!=1){
            throw new BusinessException("用户大转盘抽中奖品未领取--插入失败");
        }
        return record.getId();
    }

    /**
     * 更新大转盘中奖纪录状态和订单id
     * @param status
     * @return
     */
    public void updateRecordStatusAndGorderId(Long userTurnTableRecordId,int status,Long gorderId){
        SfUserTurnTableRecord userTurnTableRecord = selectByPrimaryKey(userTurnTableRecordId);
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
            recordInfo.setTurnTableId(record.getTurnTableId());
            recordInfo.setGiftId(record.getGiftId());
            recordInfo.setId(record.getId());
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
