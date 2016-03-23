package com.masiis.shop.scheduler.business.order;

import com.masiis.shop.dao.platform.order.PfBorderItemMapper;
import com.masiis.shop.dao.platform.order.PfBorderMapper;
import com.masiis.shop.dao.platform.user.PfUserBillMapper;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by lzh on 2016/3/23.
 */
@Service
public class PfUserBillTaskService {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private PfBorderMapper orderMapper;
    @Resource
    private PfBorderItemMapper itemMapper;

    /**
     *
     */
    public void createPfUserBillByDaily() {

    }
}
