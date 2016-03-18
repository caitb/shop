package com.masiis.shop.web.platform.service.order;

import com.masiis.shop.dao.platform.order.PfBorderConsigneeMapper;
import com.masiis.shop.dao.po.PfBorderConsignee;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * PfBorderConsigneeService
 *
 * @author ZhaoLiang
 * @date 2016/3/18
 */
@Service
public class PfBorderConsigneeService {
    @Resource
    private PfBorderConsigneeMapper pfBorderConsigneeMapper;

    public int addPfBorderConsignee(PfBorderConsignee pfBorderConsignee) throws Exception {
        return pfBorderConsigneeMapper.insert(pfBorderConsignee);
    }
}
