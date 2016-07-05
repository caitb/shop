package com.masiis.shop.web.promotion.cpromotion.service.gorder;

import com.masiis.shop.dao.mall.promotion.SfGorderOperationLogMapper;
import com.masiis.shop.dao.po.SfGorderOperationLog;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * C端活动订单操作日志表 service
 */
@Service
public class SfGorderOperationLogService {

    @Resource
    private SfGorderOperationLogMapper sfGorderOperationLogMapper;

    private int addGorderOperationLog(Long gorderId,String orderOperateType,Integer oldOrderStatus,Integer newOrderStatus,String remark){
        SfGorderOperationLog operationLog = new SfGorderOperationLog();
        operationLog.setSfGorderId(gorderId);
        operationLog.setSfOrderStatus(newOrderStatus);
        StringBuffer sb = new StringBuffer(remark);
        if (orderOperateType.equals("add")){
            //插入订单操作
            operationLog.setRemark(sb.toString());
        }else{
          //更新订单操作
            sb.append("---更新订单操作，订单状态由----").append(oldOrderStatus).append("转变为").append(newOrderStatus);
            operationLog.setRemark(sb.toString());
        }
        return sfGorderOperationLogMapper.insert(operationLog);
    }
}
