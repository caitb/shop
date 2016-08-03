package com.masiis.shop.web.promotion.cpromotion.service.guser;

import com.masiis.shop.common.enums.promotion.SfUserTurnTableTimesTypeEnum;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.mall.promotion.SfUserTurnTableMapper;
import com.masiis.shop.dao.po.SfUserTurnTable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 用户转盘信息service
 */
@Service
public class SfUserTurnTableService {

    @Resource
    private SfUserTurnTableMapper userTurnTableMapper;

    public SfUserTurnTable getSfUserTurnTable(Long userId,Integer turnTableId){
        return userTurnTableMapper.getSfUserTurnTable(userId,turnTableId);
    }
    @Transactional(propagation = Propagation.REQUIRED,readOnly = false)
    public int updateSfUserTurnTable(SfUserTurnTable sfUserTurnTable){
        return userTurnTableMapper.updateByPrimaryKey(sfUserTurnTable);
    }

    /**
     * 减少或者增加 用户转盘的次数
     * @param type
     * @param changeTimes
     * @param userId
     * @param turnTableId
     */
    public SfUserTurnTable reduceTimesOrAddTimes(Integer type,Integer changeTimes,Long userId,Integer turnTableId){
        //用户转盘增加已抽奖次数，减少未抽奖次数
        SfUserTurnTable sfUserTurnTable = getSfUserTurnTable(userId,turnTableId);
        if (sfUserTurnTable!=null){
            if (type.equals(SfUserTurnTableTimesTypeEnum.REDUCE_TIMES.getCode())){
                //未使用的次数
                sfUserTurnTable.setNotUsedTimes(sfUserTurnTable.getNotUsedTimes()-changeTimes);
                //已使用的次数
                sfUserTurnTable.setUsedTimes(sfUserTurnTable.getUsedTimes()+changeTimes);
            }
            int i = updateSfUserTurnTable(sfUserTurnTable);
            if (i!=1){
                throw new BusinessException("更新用户转盘次数失败");
            }else{
                return sfUserTurnTable;
            }
        }else{
            throw new BusinessException("更新用户转盘次数------查询失败");
        }
    }
}
