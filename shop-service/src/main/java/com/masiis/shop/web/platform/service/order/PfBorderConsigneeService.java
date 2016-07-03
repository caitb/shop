package com.masiis.shop.web.platform.service.order;

import com.masiis.shop.dao.platform.order.PfBorderConsigneeMapper;
import com.masiis.shop.dao.po.PfBorderConsignee;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by cai_tb on 16/6/25.
 */
@Service
public class PfBorderConsigneeService {

    @Resource
    private PfBorderConsigneeMapper pfBorderConsigneeMapper;

    public PfBorderConsignee getByBOrderId(Long bOrderId) {
        return pfBorderConsigneeMapper.selectByBorderId(bOrderId);
    }
}
