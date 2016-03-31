package com.masiis.shop.scheduler.business.order;

import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.dao.po.PfBorder;
import com.masiis.shop.scheduler.service.order.PfBorderService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by lzh on 2016/3/30.
 */
@Service
public class PfBOrderTaskService {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private PfBorderService bOrderService;

    /**
     * 自动取消72小时未支付订单
     */
    public void autoCancelUnPayOrder() {
        Date expiraTime = DateUtil.getDateNextdays(-3);
        log.info("计算过期时间界限点,时间点是:" + DateUtil.Date2String(expiraTime, "yyyy-MM-dd HH:mm:ss"));

        // 查询3天前创建的未支付订单
        // 查询代理订单
        List<PfBorder> bList = bOrderService.findListByStatusAndDate(expiraTime, 0, 0);

        // 查询分销订单
    }
}
