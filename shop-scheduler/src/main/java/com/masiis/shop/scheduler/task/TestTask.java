package com.masiis.shop.scheduler.task;

import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by lzh on 2016/3/22.
 */
@Component
public class TestTask {
    public void testJob(){
        System.out.println(new Date());
    }
}
