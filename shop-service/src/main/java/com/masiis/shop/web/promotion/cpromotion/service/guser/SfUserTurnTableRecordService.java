package com.masiis.shop.web.promotion.cpromotion.service.guser;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.mall.promotion.SfUserTurnTableRecordMapper;
import com.masiis.shop.dao.po.SfUserTurnTableRecord;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 用户转盘中奖记录service
 */
@Service
public class SfUserTurnTableRecordService {

    @Resource
    private SfUserTurnTableRecordMapper userTurnTableRecordMapper;


    public SfUserTurnTableRecord getRecordByUserIdAndTurnTableIdAndGiftId(Long userId,Integer turnTableId,Integer turnTableGiftId ){
        return userTurnTableRecordMapper.getRecordByUserIdAndTurnTableIdAndGiftId(userId,turnTableId,turnTableGiftId);
    }

    /**
     * 更新大转盘中奖纪录状态和订单id
     * @param userId
     * @param turnTableId
     * @param turnTableGiftId
     * @param status
     * @return
     */
    public void updateRecordStatusAndGorderId(Long userId,Integer turnTableId,Integer turnTableGiftId ,int status,Long gorderId){
        SfUserTurnTableRecord userTurnTableRecord = getRecordByUserIdAndTurnTableIdAndGiftId(userId,turnTableId,turnTableGiftId);
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
}
