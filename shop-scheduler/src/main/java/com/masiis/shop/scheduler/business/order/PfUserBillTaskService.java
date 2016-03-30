package com.masiis.shop.scheduler.business.order;

import com.masiis.shop.common.interfaces.IParallelThread;
import com.masiis.shop.common.util.CurrentThreadUtils;
import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.dao.platform.order.PfBorderItemMapper;
import com.masiis.shop.dao.platform.order.PfBorderMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.platform.user.PfUserBillMapper;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.PfBorder;
import com.masiis.shop.dao.po.PfUserBill;
import com.masiis.shop.dao.po.PfUserBillItem;
import com.masiis.shop.scheduler.service.order.PfBorderService;
import com.masiis.shop.scheduler.service.order.PfUserBillItemService;
import com.masiis.shop.scheduler.service.order.PfUserBillService;
import com.masiis.shop.scheduler.service.user.ComUserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by lzh on 2016/3/23.
 */
@Service
public class PfUserBillTaskService {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private PfBorderService orderService;
    @Resource
    private PfUserBillService billService;
    @Resource
    private PfUserBillItemService itemService;
    @Resource
    private ComUserService userService;

    /**
     * 创建每日结算账单
     */
    public void createPfUserBillByDaily() {
        // 账单创建日期
        Date billCreateTime = new Date();
        // 获取结算日(当前时间前一天)
        final Date balanceDate = DateUtil.getDateNextdays(-1);
        log.info("创建每日结算账单,账单结算时间:" + DateUtil.Date2String(balanceDate, DateUtil.DEFAULT_DATE_FMT_2));
        // 订单开始时间
        final Date countStartDay = getCountDay(balanceDate);
        log.info("创建每日结算账单,订单开始时间:" + DateUtil.Date2String(countStartDay, DateUtil.DEFAULT_DATE_FMT_2));
        // 订单结束时间
        final Date countEndDay = DateUtil.getDateNextdays(countStartDay, 1);
        log.info("创建每日结算账单,订单结束时间:" + DateUtil.Date2String(countStartDay, DateUtil.DEFAULT_DATE_FMT_2));
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

    public static Date getCountDay(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) - 8);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    public static void main(String[] a) {
        //System.out.println(getCountDay(new Date()));
        //System.out.println(DateUtil.getDateNextdays(getCountDay(new Date()), 1));
        System.out.println(DateUtil.getDateNextdays(-1));
    }
}
