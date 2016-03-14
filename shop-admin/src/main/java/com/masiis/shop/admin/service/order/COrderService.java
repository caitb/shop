package com.masiis.shop.admin.service.order;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.masiis.shop.admin.beans.order.Order;
import com.masiis.shop.admin.service.base.BaseService;
import com.masiis.shop.dao.platform.order.PfCorderConsigneeMapper;
import com.masiis.shop.dao.platform.order.PfCorderMapper;
import com.masiis.shop.dao.po.PfCorder;
import com.masiis.shop.dao.po.PfCorderConsignee;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by caitingbiao on 2016/3/2.
 */
@Service
public class COrderService extends BaseService {

    @Resource
    private PfCorderMapper pfCorderMapper;
    @Resource
    private PfCorderConsigneeMapper pfCorderConsigneeMapper;

    public Map<String, Object> listByCondition(Integer pageNumber, Integer pageSize, PfCorder pfCorder){
        PageHelper.startPage(pageNumber, pageSize);
        List<PfCorder> pfCorders = pfCorderMapper.selectByCondition(pfCorder);
        PageInfo<PfCorder> pageInfo = new PageInfo<>(pfCorders);

        List<Order> orders = new ArrayList<>();
        for(PfCorder pc : pfCorders){
            PfCorderConsignee pfCorderConsignee = pfCorderConsigneeMapper.selectByCorderId(pc.getId());

            Order order = new Order();
            order.setPfCorder(pc);
            order.setPfCorderConsignee(pfCorderConsignee);

            orders.add(order);
        }

        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("total", pageInfo.getTotal());
        pageMap.put("rows", orders);

        return pageMap;
    }
}
