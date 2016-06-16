package com.masiis.shop.scheduler.platform.business.user;

import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.dao.po.PfUserUpgradeNotice;
import com.masiis.shop.scheduler.platform.service.user.PfUserUpgradeNoticeService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 代理升级通知单定时任务类
 *
 * @Date 2016/6/16
 * @Author lzh
 */
@Service
public class PfUserUpgradeTaskService {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private PfUserUpgradeNoticeService pfUserUpgradeNoticeService;

    /**
     * 处理2天未处理的升级通知单
     */
    public void handleUnsolvedUpgradeNotice() {
        Date time = DateUtil.getDateNextdays(new Date(), -2);
        log.info("计算时间临界点:" + time);

        // 查询要处理的升级通知单
        List<PfUserUpgradeNotice> notices = pfUserUpgradeNoticeService.findAllUnsolvedNoticesByDate();

    }


    public static void main(String... args){
        System.out.println(DateUtil.getDateNextdays(new Date(), -2));
    }
}
