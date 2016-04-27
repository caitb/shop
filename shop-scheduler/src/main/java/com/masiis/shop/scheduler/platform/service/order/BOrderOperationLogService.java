package com.masiis.shop.scheduler.platform.service.order;

import com.masiis.shop.dao.platform.order.PfBorderOperationLogMapper;
import com.masiis.shop.dao.po.PfBorder;
import com.masiis.shop.dao.po.PfBorderOperationLog;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * BOrderOperationLogService
 *
 * @author ZhaoLiang
 * @date 2016/4/27
 */
@Service
public class BOrderOperationLogService {
    @Resource
    private PfBorderOperationLogMapper pfBorderOperationLogMapper;

    /**
     * 添加订单日志
     * @param pfBorder
     * @param remark 日志备注
     */
    public void insertBOrderOperationLog(PfBorder pfBorder, String remark) {
        PfBorderOperationLog pfBorderOperationLog = new PfBorderOperationLog();
        pfBorderOperationLog.setCreateMan(pfBorder.getUserId());
        pfBorderOperationLog.setCreateTime(new Date());
        pfBorderOperationLog.setPfBorderStatus(pfBorder.getOrderStatus());
        pfBorderOperationLog.setPfBorderId(pfBorder.getId());
        pfBorderOperationLog.setRemark(remark);
        pfBorderOperationLogMapper.insert(pfBorderOperationLog);
    }
}
