package com.masiis.shop.scheduler.mall.business.order;

import com.masiis.shop.common.interfaces.IParallelThread;
import com.masiis.shop.common.util.CurrentThreadUtils;
import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.scheduler.mall.service.order.SfOrderService;
import com.masiis.shop.scheduler.mall.service.user.SfUserBillService;
import com.masiis.shop.scheduler.platform.service.user.ComUserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @Date:2016/4/14
 * @auth:lzh
 */
@Service
public class SfUserBillTaskService {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private SfOrderService sfOrderService;
    @Resource
    private SfUserBillService billService;
    @Resource
    private ComUserService userService;

    public void createSfUserBillByDaily() {
// 账单创建日期
        Date billCreateTime = new Date();
        // 获取结算日(当前时间前一天)
        final Date balanceDate = DateUtil.getDateNextdays(-1);
        log.info("创建每日结算账单,账单结算时间:" + DateUtil.Date2String(balanceDate, DateUtil.DEFAULT_DATE_FMT_2));
        // 订单开始时间
        final Date countStartDay = getCountDay(balanceDate, -8);
        log.info("创建每日结算账单,订单开始时间:" + DateUtil.Date2String(countStartDay, DateUtil.DEFAULT_DATE_FMT_2));
        // 订单结束时间
        final Date countEndDay = DateUtil.getDateNextdays(countStartDay, 1);
        log.info("创建每日结算账单,订单结束时间:" + DateUtil.Date2String(countEndDay, DateUtil.DEFAULT_DATE_FMT_2));

        // 检查日期区间内是否有账单,确保只创建一次
        Long nums = billService.queryBillNumsByDate(getCountDay(new Date(), 0), getCountDay(new Date(), 1));
        if(nums.intValue() != 0){
            log.error("此日期内已存在账单,异常发生");
            // 创建通知
            return;
        }

        // 查询所有用户
        List<ComUser> users = userService.findAll();
        // 多线程处理
        CurrentThreadUtils.parallelJob(new IParallelThread() {
            public Boolean doMyJob(Object obj) throws Exception {
                ComUser pa = (ComUser) obj;
                try {
                    log.info("创建个人日结算单开始,用户id:" + pa.getId());
                    // 创建结算日的日账单
                    billService.createBillByUserAndDate(pa, countStartDay, countEndDay, balanceDate);
                    log.info("创建个人日结算单成功");
                    return true;
                } catch (Exception e) {
                    log.error("创建个人日结算单失败," + e.getMessage());
                    return false;
                }
            }
        }, new LinkedBlockingDeque<Object>(users), 0);
        // 结束
    }

    public static Date getCountDay(Date date, int num){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) + num);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }
}
