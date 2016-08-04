package com.masiis.shop.web.promotion.cpromotion.service.guser;

import com.masiis.shop.common.enums.promotion.SfUserTurnTableTimesTypeEnum;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.mall.promotion.SfUserTurnTableMapper;
import com.masiis.shop.dao.po.SfUserTurnTable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

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

    public int insert(SfUserTurnTable sfUserTurnTable){
        return userTurnTableMapper.insert(sfUserTurnTable);
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
        int i = 0;
        SfUserTurnTable sfUserTurnTable = null;
        if (type.equals(SfUserTurnTableTimesTypeEnum.REDUCE_TIMES.getCode())){
            sfUserTurnTable   = getSfUserTurnTable(userId,turnTableId);
            if (sfUserTurnTable!=null){
                //未使用的次数
                sfUserTurnTable.setNotUsedTimes(sfUserTurnTable.getNotUsedTimes()-changeTimes);
                //已使用的次数
                sfUserTurnTable.setUsedTimes(sfUserTurnTable.getUsedTimes()+changeTimes);
                i = updateSfUserTurnTable(sfUserTurnTable);
            }
        }else if (type.equals(SfUserTurnTableTimesTypeEnum.ADD_TIMES.getCode())){
                sfUserTurnTable = new SfUserTurnTable();
                sfUserTurnTable.setTurnTableId(turnTableId);
                sfUserTurnTable.setCreateTime(new Date());
                sfUserTurnTable.setCreateMan(userId);
                sfUserTurnTable.setNotUsedTimes(0);
                //未使用的次数
                sfUserTurnTable.setNotUsedTimes(sfUserTurnTable.getNotUsedTimes()-changeTimes);
                i = insert(sfUserTurnTable);
            }
        if (i!=1){
            throw new BusinessException("更新或增加用户转盘次数失败");
        }else{
            return sfUserTurnTable;
        }
    }
}
