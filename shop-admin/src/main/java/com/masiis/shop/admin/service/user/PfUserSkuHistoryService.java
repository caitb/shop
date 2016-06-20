package com.masiis.shop.admin.service.user;

import com.masiis.shop.dao.platform.user.PfUserSkuHistoryMapper;
import com.masiis.shop.dao.po.PfUserSkuHistory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by hzz on 2016/6/17.
 */
@Service
@Transactional
public class PfUserSkuHistoryService {

    @Resource
    private PfUserSkuHistoryMapper pfUserSkuHistoryMapper;

    public int insert(PfUserSkuHistory po){
        return pfUserSkuHistoryMapper.insert(po);
    }
}
