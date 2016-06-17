package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.dao.platform.user.PfUserRebateMapper;
import com.masiis.shop.dao.po.PfUserRebate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.annotation.Resource;

/**
 * Created by hzz on 2016/6/16.
 */
@Service
@Transactional
public class PfUserRebateService {

    @Resource
    private PfUserRebateMapper pfUserRebateMapper;

    public int insert(PfUserRebate po){
        return pfUserRebateMapper.insert(po);
    }
}
