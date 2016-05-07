package com.masiis.shop.scheduler.task.platform;

import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.scheduler.platform.service.agent.PfStatisticsAgentService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by wangbingjian on 2016/4/18.
 */
@Component
public class PfAgentTask {

    private Logger log =  Logger.getLogger(this.getClass());
    @Autowired
    private PfStatisticsAgentService pfStatisticsAgentService;
    /**
     * 每日统计下级代理人数
     */
    public void agentStatisticsJob(){
        log.info("每日统计下级代理人数定时任务开始,开始时间为:" + DateUtil.Date2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
        try{
            pfStatisticsAgentService.statisticsAgent();
            log.info("每日统计下级代理人数定时任务成功");
        } catch (Exception e) {
            log.error("每日统计下级代理人数定时任务错误,{}" + e.getMessage());
        }
        log.info("每日统计下级代理人数定时任务结束,结束时间为:" + DateUtil.Date2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
    }
}
