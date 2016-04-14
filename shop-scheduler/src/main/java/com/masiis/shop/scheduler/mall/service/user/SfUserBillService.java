package com.masiis.shop.scheduler.mall.service.user;

import com.masiis.shop.dao.po.ComUser;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Date:2016/4/14
 * @auth:lzh
 */
@Service
public class SfUserBillService {
    private Logger log = Logger.getLogger(this.getClass());

    public void createBillByUserAndDate(ComUser pa, Date countStartDay, Date countEndDay, Date balanceDate) {
        try{

        } catch (Exception e) {
            log.error(e);
        }
    }
}
