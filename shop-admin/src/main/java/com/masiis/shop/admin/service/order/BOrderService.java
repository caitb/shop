package com.masiis.shop.admin.service.order;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.masiis.shop.admin.beans.order.Order;
import com.masiis.shop.admin.beans.product.ProductInfo;
import com.masiis.shop.dao.platform.order.*;
import com.masiis.shop.dao.platform.product.ComSkuMapper;
import com.masiis.shop.dao.platform.product.ComSpuMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.po.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by ZhaoLiang on 2016/3/2.
 */
@Service
public class BOrderService {

    @Resource
    private PfBorderMapper pfBorderMapper;
    @Resource
    private ComUserMapper comUserMapper;
    @Resource
    private PfBorderPaymentMapper pfBorderPaymentMapper;
    @Resource
    private PfBorderConsigneeMapper pfBorderConsigneeMapper;
    @Resource
    private PfBorderFreightMapper pfBorderFreightMapper;
    @Resource
    private PfBorderItemMapper pfBorderItemMapper;
    @Resource
    private ComSkuMapper comSkuMapper;
    @Resource
    private ComSpuMapper comSpuMapper;

    /**
     * 根据条件查询记录
     * @param pageNo
     * @param pageSize
     * @param pfBorder
     * @return
     */
    public Map<String, Object> listByCondition(Integer pageNo, Integer pageSize, PfBorder pfBorder){
        PageHelper.startPage(pageNo, pageSize);
        List<PfBorder> pfBorders = pfBorderMapper.selectByCondition(pfBorder);
        PageInfo<PfBorder> pageInfo = new PageInfo<>(pfBorders);

        List<Order> orders = new ArrayList<>();
        for(PfBorder pbo : pfBorders){
            ComUser comUser = comUserMapper.selectByPrimaryKey(pbo.getUserId());
            PfBorderConsignee pfBorderConsignee = pfBorderConsigneeMapper.selectByBorderId(pbo.getId());
            List<PfBorderPayment> pfBorderPayments = pfBorderPaymentMapper.selectByBorderId(pbo.getId());

            Order order = new Order();
            order.setPfBorder(pbo);
            order.setComUser(comUser);
            order.setPfBorderConsignee(pfBorderConsignee);
            order.setPfBorderPayments(pfBorderPayments);

            orders.add(order);
        }

        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("total", pageInfo.getTotal());
        pageMap.put("rows", orders);

        return pageMap;
    }

    /**
     * 获取订单明细
     * @param id
     * @return
     */
    public Order find(Long id){
        PfBorder pfBorder = pfBorderMapper.selectByPrimaryKey(id);
        ComUser comUser = comUserMapper.selectByPrimaryKey(pfBorder.getUserId());
        PfBorderConsignee pfBorderConsignee = pfBorderConsigneeMapper.selectByBorderId(id);
        List<PfBorderFreight> pfBorderFreights = pfBorderFreightMapper.selectByBorderId(id);
        List<PfBorderItem> pfBorderItems = pfBorderItemMapper.selectAllByOrderId(id);

        List<ProductInfo> productInfos = new ArrayList<>();
        for(PfBorderItem pfBorderItem : pfBorderItems){
            ComSku comSku = comSkuMapper.selectById(pfBorderItem.getSkuId());
            ComSpu comSpu = comSpuMapper.selectById(pfBorderItem.getSpuId());

            ProductInfo productInfo = new ProductInfo();
            productInfo.setComSku(comSku);
            productInfo.setComSpu(comSpu);

            productInfos.add(productInfo);
        }

        Order order = new Order();
        order.setPfBorder(pfBorder);
        order.setComUser(comUser);
        order.setPfBorderConsignee(pfBorderConsignee);
        order.setPfBorderFreights(pfBorderFreights);
        order.setPfBorderItems(pfBorderItems);
        order.setProductInfos(productInfos);

        if(pfBorder.getPayStatus().intValue() == 1){
            PfBorderPayment borderPaymentC = new PfBorderPayment();
            borderPaymentC.setPfBorderId(pfBorder.getId());
            borderPaymentC.setIsEnabled(1);
            List<PfBorderPayment> pfBorderPayments = pfBorderPaymentMapper.selectByCondition(borderPaymentC);
            order.setPfBorderPayments(pfBorderPayments);
        }

        return order;
    }

    /**
     * 发货
     * @param pfBorderFreight
     */
    public void delivery(PfBorderFreight pfBorderFreight){
        PfBorder pfBorder = new PfBorder();
        pfBorder.setId(pfBorderFreight.getPfBorderId());
        pfBorder.setOrderStatus(8);
        pfBorder.setShipStatus(5);
        pfBorder.setShipTime(new Date());

        pfBorderFreight.setCreateTime(new Date());

        pfBorderMapper.updateById(pfBorder);
        pfBorderFreightMapper.insert(pfBorderFreight);
    }
}
