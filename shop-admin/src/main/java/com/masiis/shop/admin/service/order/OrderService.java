package com.masiis.shop.admin.service.order;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.masiis.shop.admin.beans.order.Order;
import com.masiis.shop.dao.mall.order.SfOrderConsigneeMapper;
import com.masiis.shop.dao.mall.order.SfOrderFreightMapper;
import com.masiis.shop.dao.mall.order.SfOrderMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.SfOrder;
import com.masiis.shop.dao.po.SfOrderConsignee;
import com.masiis.shop.dao.po.SfOrderFreight;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 店铺订单业务
 * Created by cai_tb on 16/4/14.
 */
@Service
public class OrderService {

    @Resource
    private ComUserMapper comUserMapper;
    @Resource
    private SfOrderMapper sfOrderMapper;
    @Resource
    private SfOrderConsigneeMapper sfOrderConsigneeMapper;
    @Resource
    private SfOrderFreightMapper sfOrderFreightMapper;

    /**
     * 店铺订单列表
     * @param pageNumber
     * @param pageSize
     * @param conditionMap
     * @return
     */
    public Map<String, Object> listByCondition(Integer pageNumber, Integer pageSize, Map<String, Object> conditionMap){
        PageHelper.startPage(pageNumber, pageSize);
        List<SfOrder> sfOrders = sfOrderMapper.selectByMap(conditionMap);
        PageInfo<SfOrder> pageInfo = new PageInfo<>(sfOrders);

        List<Order> orders = new ArrayList<>();
        for(SfOrder sfOrder : sfOrders){
            ComUser comUser = comUserMapper.selectByPrimaryKey(sfOrder.getUserId());
            SfOrderConsignee sfOrderConsignee = sfOrderConsigneeMapper.getOrdConByOrdId(sfOrder.getId());
            List<SfOrderFreight> sfOrderFreights = sfOrderFreightMapper.selectByOrderId(sfOrder.getId());

            Order order = new Order();
            order.setComUser(comUser);
            order.setSfOrderConsignee(sfOrderConsignee);
            order.setSfOrderFreights(sfOrderFreights);

            orders.add(order);
        }

        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("total", pageInfo.getTotal());
        pageMap.put("rows", orders);

        return pageMap;
    }
}
