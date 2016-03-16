package com.masiis.shop.admin.task;

import com.masiis.shop.common.util.DateUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by lzh on 2016/3/16.
 */
@Component
public class TestTask {
    @Scheduled(cron = "0/10 * * * * ?")
    public void testTask(){
        System.out.println("当前时间" + DateUtil.Date2String(new Date(), "yyyy-MM-dd HH:mm:ss.SSS"));
    }
}
