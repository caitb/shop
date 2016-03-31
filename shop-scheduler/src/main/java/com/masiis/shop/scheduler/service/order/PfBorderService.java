package com.masiis.shop.scheduler.service.order;

import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.dao.platform.order.PfBorderMapper;
import com.masiis.shop.dao.po.PfBorder;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by lzh on 2016/3/23.
 */
@Service
public class PfBorderService {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private PfBorderMapper borderMapper;

    public List<PfBorder> findListByStatusAndDate(Date expiraTime, Integer orderStatus, Integer payStatus) {
        log.info("查询创建时间大于:" + DateUtil.Date2String(expiraTime, "yyyy-MM-dd HH:mm:ss")
                + ",订单状态为:" + orderStatus + ",支付状态为:" + payStatus + "的订单");
        // 查询
        List<PfBorder> resList = borderMapper.selectByStatusAndDate(expiraTime, orderStatus, payStatus);
        if(resList == null || resList.size() == 0)
            return null;
        return resList;
    }

    /**
     * 取消未支付订单
     *
     * @param bOrder
     */
    @Transactional
    public void cancelUnPayBOrder(PfBorder bOrder) {
        try{
            // 重新根据id查询该订单
            // 检查订单状态的有效性
            // 修改订单的状态为已取消状态
            // 插入订单操作记录
        } catch (Exception e) {

        }
    }
}
