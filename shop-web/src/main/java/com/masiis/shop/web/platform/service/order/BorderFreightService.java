package com.masiis.shop.web.platform.service.order;

import com.masiis.shop.dao.platform.order.PfBorderFreightMapper;
import com.masiis.shop.dao.po.PfBorderFreight;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 快递公司信息
 * @author muchaofeng
 * @date 2016/3/20 14:27
 */
@Service
@Transactional
public class BorderFreightService {
    @Resource
    private PfBorderFreightMapper pfBorderFreightMapper;

    public void addPfBorderFreight(PfBorderFreight pfBorderFreight) throws Exception{
        pfBorderFreightMapper.insert(pfBorderFreight);
    }
}
