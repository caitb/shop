package com.masiis.shop.scheduler.task.mall;

import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.scheduler.mall.service.user.SfUserIsBuyService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by wangbingjian on 2016/5/7.
 */
@Component
public class SfUserTask {
    private static final Logger logger = Logger.getLogger(SfUserTask.class);
    @Autowired
    private SfUserIsBuyService sfUserIsBuyService;

    public void userIsBuyJob(){
        logger.info("设置用户提现权限,开始时间为:" + DateUtil.Date2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
        try{
            sfUserIsBuyService.userIsBuyComplete();
            logger.info("设置用户提现权限定时任务成功");
        } catch (Exception e) {
            logger.error("设置用户提现权限定时任务错误,{}" + e.getMessage());
        }
        logger.info("设置用户提现权限,结束时间为:" + DateUtil.Date2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
    }
}
