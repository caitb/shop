package com.masiis.shop.web.promotion.cpromotion.service.guser;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.mall.promotion.SfUserTurnTableItemMapper;
import com.masiis.shop.dao.po.SfUserTurnTableItem;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 用户转盘明细 service
 */
@Service
public class SfUserTurnTableItemService {

    @Resource
    private SfUserTurnTableItemMapper userTurnTableItemMapper;

    public int insert(SfUserTurnTableItem sfUserTurnTableItem){
        return userTurnTableItemMapper.insert(sfUserTurnTableItem);
    }

    public void insert(Integer type,Integer changeTimes,Integer turnTableId,Integer turnTableRuleId,Long userTurnTableId){
        SfUserTurnTableItem userTurnTableItem = new SfUserTurnTableItem();
        userTurnTableItem.setCreateTime(new Date());
        userTurnTableItem.setTimes(changeTimes);
        userTurnTableItem.setTurnTableId(turnTableId);
        userTurnTableItem.setTurnTableRuleId(turnTableRuleId);
        userTurnTableItem.setType(type);
        userTurnTableItem.setUserTurnTableId(userTurnTableId);
        int i = userTurnTableItemMapper.insert(userTurnTableItem);
        if (i!=1){
            throw new BusinessException("增加用户转盘具体信息:更新次数失败");
        }
    }
}
