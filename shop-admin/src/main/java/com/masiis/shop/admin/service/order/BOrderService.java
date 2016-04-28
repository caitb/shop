package com.masiis.shop.admin.service.order;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.masiis.shop.admin.beans.order.Order;
import com.masiis.shop.admin.beans.product.ProductInfo;
import com.masiis.shop.admin.utils.WxPFNoticeUtils;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.MobileMessageUtil;
import com.masiis.shop.dao.mall.order.SfOrderItemMallMapper;
import com.masiis.shop.dao.mall.user.SfUserRelationMapper;
import com.masiis.shop.dao.platform.order.*;
import com.masiis.shop.dao.platform.product.ComAgentLevelMapper;
import com.masiis.shop.dao.platform.product.ComSkuMapper;
import com.masiis.shop.dao.platform.product.ComSpuMapper;
import com.masiis.shop.dao.platform.product.PfSkuStockMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuStockMapper;
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
    @Resource
    private PfSkuStockMapper pfSkuStockMapper;
    @Resource
    private PfUserSkuStockMapper pfUserSkuStockMapper;
    @Resource
    private BOrderOperationLogService bOrderOperationLogService;
    @Resource
    private ComAgentLevelMapper comAgentLevelMapper;

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
    public void delivery(PfBorderFreight pfBorderFreight) throws Exception {
        PfBorder pfBorder = pfBorderMapper.selectByPrimaryKey(pfBorderFreight.getPfBorderId());
        pfBorder.setOrderStatus(8);
        pfBorder.setShipStatus(5);
        pfBorder.setShipTime(new Date());

        pfBorderFreight.setCreateTime(new Date());

        pfBorderMapper.updateById(pfBorder);
        pfBorderFreightMapper.insert(pfBorderFreight);

        updateOrderStock(pfBorder);

        //添加订单日志
        bOrderOperationLogService.insertBOrderOperationLog(pfBorder,"订单完成");

        //通知客户 * @param params   (1,商品名称;2,代理等级;3,订单编号(不是id);4,快递公司;5,快递单号)
        PfBorderConsignee pfBorderConsignee = pfBorderConsigneeMapper.selectByBorderId(pfBorder.getId());
        ComUser comUser = comUserMapper.selectByPrimaryKey(pfBorderConsignee.getUserId());
        List<PfBorderItem> borderItems = pfBorderItemMapper.selectAllByOrderId(pfBorder.getId());
        String skuNames = "";
        String levelNames = "";
        for(PfBorderItem borderItem : borderItems){
            skuNames += borderItem.getSkuName();
            levelNames += comAgentLevelMapper.selectByPrimaryKey(borderItem.getAgentLevelId()).getName();
        }
        MobileMessageUtil.goodsOrderShipRemind(pfBorderConsignee.getMobile(), pfBorder.getOrderCode(), pfBorderFreight.getShipManName(), pfBorderFreight. getFreight());
        WxPFNoticeUtils.getInstance().orderShippedNotice(comUser, new String[]{skuNames, levelNames, pfBorder.getOrderCode(), pfBorderFreight.getShipManName(), pfBorderFreight.getFreight()}, "http://m.qc.iimai.com/borderManage/borderDetils.html?id="+pfBorder.getId());
    }

    /**
     * 更新库存
     * @param pfBorder
     */
    public  void  updateOrderStock(PfBorder pfBorder) throws Exception {
        List<PfBorderItem> pfBorderItems = pfBorderItemMapper.selectAllByOrderId(pfBorder.getId());
        if(pfBorder.getUserPid().intValue() == 0 && pfBorder.getOrderType().intValue() != 2){
            for(PfBorderItem pfBorderItem : pfBorderItems){
                PfSkuStock pfSkuStock = pfSkuStockMapper.selectBySkuId(pfBorderItem.getSkuId());
                if(pfSkuStock.getStock()-pfBorderItem.getQuantity()>=0 && pfSkuStock.getFrozenStock()-pfBorderItem.getQuantity()>=0){
                    pfSkuStock.setStock(pfSkuStock.getStock()-pfBorderItem.getQuantity());
                    pfSkuStock.setFrozenStock(pfSkuStock.getFrozenStock()-pfBorderItem.getQuantity());
                    int c = pfSkuStockMapper.updateByIdAndVersion(pfSkuStock);
                    if(c == 0) throw new Exception("更改库存失败!");
                }else{
                    throw new Exception("库存异常!");
                }
            }
        }else{
            for(PfBorderItem pfBorderItem : pfBorderItems){
                Long userPid = pfBorder.getOrderType().intValue()==2 ? pfBorder.getUserId() : pfBorder.getUserPid();
                PfUserSkuStock pfUserSkuStock = pfUserSkuStockMapper.selectByUserIdAndSkuId(userPid, pfBorderItem.getSkuId());
                if(pfUserSkuStock.getStock()-pfBorderItem.getQuantity()>=0 && pfUserSkuStock.getFrozenStock()-pfBorderItem.getQuantity()>=0){
                    pfUserSkuStock.setStock(pfUserSkuStock.getStock()-pfBorderItem.getQuantity());
                    pfUserSkuStock.setFrozenStock(pfUserSkuStock.getFrozenStock()-pfBorderItem.getQuantity());
                    int c = pfUserSkuStockMapper.updateByIdAndVersion(pfUserSkuStock);
                    if(c == 0) throw new Exception("更改库存失败!");
                }else{
                    throw new Exception("库存异常!");
                }
            }
        }
    }
}
