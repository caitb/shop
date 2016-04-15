package com.masiis.shop.admin.service.order;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.masiis.shop.admin.beans.order.Order;
import com.masiis.shop.admin.beans.product.ProductInfo;
import com.masiis.shop.admin.service.base.BaseService;
import com.masiis.shop.dao.platform.order.*;
import com.masiis.shop.dao.platform.product.ComSkuMapper;
import com.masiis.shop.dao.platform.product.ComSpuMapper;
import com.masiis.shop.dao.platform.product.PfSkuStockMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.po.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by caitingbiao on 2016/3/2.
 */
@Service
public class COrderService extends BaseService {

    @Resource
    private ComUserMapper comUserMapper;
    @Resource
    private PfCorderMapper pfCorderMapper;
    @Resource
    private PfCorderPaymentMapper pfCorderPaymentMapper;
    @Resource
    private PfCorderConsigneeMapper pfCorderConsigneeMapper;
    @Resource
    private PfCorderFreightMapper pfCorderFreightMapper;
    @Resource
    private ComSkuMapper comSkuMapper;
    @Resource
    private ComSpuMapper comSpuMapper;
    @Resource
    private PfCorderOperationLogMapper pfCorderOperationLogMapper;

    public Map<String, Object> listByCondition(Integer pageNumber, Integer pageSize, PfCorder pfCorder){
        PageHelper.startPage(pageNumber, pageSize);
        List<PfCorder> pfCorders = pfCorderMapper.selectByCondition(pfCorder);
        PageInfo<PfCorder> pageInfo = new PageInfo<>(pfCorders);

        List<Order> orders = new ArrayList<>();
        for(PfCorder pc : pfCorders){
            ComUser comUser = comUserMapper.selectByPrimaryKey(pc.getUserId());
            PfCorderConsignee pfCorderConsignee = pfCorderConsigneeMapper.selectByCorderId(pc.getId());
            List<PfCorderFreight> pfCorderFreights = pfCorderFreightMapper.selectByCorderId(pc.getId());

            Order order = new Order();
            order.setComUser(comUser);
            order.setPfCorder(pc);
            order.setPfCorderConsignee(pfCorderConsignee);
            order.setPfCorderFreights(pfCorderFreights);

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
        PfCorder pfCorder = pfCorderMapper.selectById(id);
        PfCorderConsignee pfCorderConsignee = pfCorderConsigneeMapper.selectByCorderId(id);
        List<PfCorderFreight> pfCorderFreights = pfCorderFreightMapper.selectByCorderId(id);

        ComUser comUser = comUserMapper.selectByPrimaryKey(pfCorder.getUserId());

        ProductInfo productInfo = new ProductInfo();
        ComSku comSku = comSkuMapper.selectById(pfCorder.getSkuId());
        ComSpu comSpu = comSpuMapper.selectById(comSku.getSpuId());
        productInfo.setComSku(comSku);
        productInfo.setComSpu(comSpu);

        Order order = new Order();
        order.setComUser(comUser);
        order.setPfCorder(pfCorder);
        order.setPfCorderConsignee(pfCorderConsignee);
        order.setPfCorderFreights(pfCorderFreights);

        if(pfCorder.getPayStatus().intValue() == 1){
            PfCorderPayment corderPaymentC = new PfCorderPayment();
            corderPaymentC.setPfCorderId(pfCorder.getId());
            corderPaymentC.setIsEnabled(1);
            List<PfCorderPayment> pfCorderPayments = pfCorderPaymentMapper.selectByCondition(corderPaymentC);
            order.setPfCorderPayments(pfCorderPayments);
        }

        List<ProductInfo> productInfos = new ArrayList<>();
        productInfos.add(productInfo);
        order.setProductInfos(productInfos);

        return order;
    }

    /**
     * 发货
     * @param pfCorderFreight
     */
    public void delivery(PfCorderFreight pfCorderFreight){
        PfCorder pfCorder = pfCorderMapper.selectById(pfCorderFreight.getPfCorderId());
        pfCorder.setOrderStatus(8);
        pfCorder.setShipStatus(5);
        pfCorder.setDeliveryTime(new Date());

        pfCorderFreight.setCreateTime(new Date());

        pfCorderMapper.updateById(pfCorder);
        pfCorderFreightMapper.insert(pfCorderFreight);

        //添加订单日志
        PfCorderOperationLog pfCorderOperationLog = new PfCorderOperationLog();
        pfCorderOperationLog.setCreateMan(pfCorder.getUserId());
        pfCorderOperationLog.setCreateTime(new Date());
        pfCorderOperationLog.setPfCorderStatus(8);
        pfCorderOperationLog.setPfCorderId(pfCorder.getId());
        pfCorderOperationLog.setRemark("订单完成");
        pfCorderOperationLogMapper.insert(pfCorderOperationLog);
    }

}
