package com.masiis.shop.web.promotion.cpromotion.service.gorder;

import com.masiis.shop.dao.mall.promotion.SfGorderOperationLogMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * C端活动订单操作日志表 service
 */
@Service
public class SfGorderOperationLogService {

    @Resource
    private SfGorderOperationLogMapper sfGorderOperationLogMapper;
}
