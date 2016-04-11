package com.masiis.shop.web.mall.service.order;

import com.masiis.shop.dao.mall.order.SfOrderOperationLogMapper;
import com.masiis.shop.dao.po.SfOrderOperationLog;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by hzz on 2016/4/10.
 */
@Service
public class SfOrderOperationLogService {

    @Resource
    private SfOrderOperationLogMapper ordOperLogMapper;

    @Transactional(propagation = Propagation.REQUIRED,readOnly = false)
    public int insert(SfOrderOperationLog sfOrderOperationLog){
        return ordOperLogMapper.insert(sfOrderOperationLog);
    }

    @Transactional(propagation = Propagation.REQUIRED,readOnly = true)
    public SfOrderOperationLog getOrdOperLogByOrderId(Long sfOrderId){
        return ordOperLogMapper.getOrdOperLogByOrderId(sfOrderId);
    }

    @Transactional(propagation = Propagation.REQUIRED,readOnly = false)
    public int  update(SfOrderOperationLog ordOperLog){
       return ordOperLogMapper.updateByPrimaryKey(ordOperLog);
    }
}
