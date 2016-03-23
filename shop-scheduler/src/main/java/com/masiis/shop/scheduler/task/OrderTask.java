package com.masiis.shop.scheduler.task;

import com.masiis.shop.dao.platform.order.PfBorderMapper;
import com.masiis.shop.dao.po.PfBorder;
import com.masiis.shop.scheduler.business.order.PfUserBillTaskService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by lzh on 2016/3/23.
 */
@Component
public class OrderTask {
    private Logger log =  Logger.getLogger(this.getClass());

    @Resource
    private PfUserBillTaskService billService;
    /**
     * 创建每日结算账单
     */
    public void createPfUserBillByDaily(){

    }
}
