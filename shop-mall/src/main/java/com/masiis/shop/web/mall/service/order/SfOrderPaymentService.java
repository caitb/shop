package com.masiis.shop.web.mall.service.order;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.po.SfOrder;
import com.masiis.shop.dao.po.SfOrderOperationLog;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by hzz on 2016/4/10.
 */
@Service
public class SfOrderPaymentService {

    @Resource
    private SfOrderService ordService;
    @Resource
    private SfOrderOperationLogService ordOperLogService;

    /**
     * 订单支付成功修改状态
     * @author hanzengzhi
     * @date 2016/4/10 11:21
     */
    @Transactional(propagation = Propagation.REQUIRED,readOnly = false)
    public void ordPaySuccModStatus(Long orderId){
        //获得订单信息
        try{
            SfOrder order = ordService.getOrderById(orderId);
            int i = updateOrder(order);
            SfOrderOperationLog ordOperLog = ordOperLogService.getOrdOperLogByOrderId(order.getId());
        }catch (Exception e){
            throw new BusinessException(e);
        }
    }
    /**
     * 更新订单状态
     * @author hanzengzhi
     * @date 2016/4/10 11:47
     */
    private int updateOrder(SfOrder order){
        order.setModifyTime(new Date());
        order.setPayTime(new Date());
        order.setOrderStatus(7);//待发货
        order.setPayStatus(1);//已支付
        return  ordService.update(order);
    }
    /**
     * 修改订单的操作日志
     * @author hanzengzhi
     * @date 2016/4/10 12:02
     */
    private int updateOrdOperLog(SfOrderOperationLog ordOperLog){
        StringBuffer sb = new StringBuffer("将订单的状态由");
        sb.append(ordOperLog.getSfOrderStatus());
        sb.append("修改为").append(1);
        ordOperLog.setSfOrderStatus(1);
        return ordOperLogService.update(ordOperLog);
    }
}
