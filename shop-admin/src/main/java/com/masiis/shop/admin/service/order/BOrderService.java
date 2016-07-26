package com.masiis.shop.admin.service.order;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.masiis.shop.admin.beans.order.Order;
import com.masiis.shop.admin.beans.product.ProductInfo;
import com.masiis.shop.admin.service.product.PfSkuStockService;
import com.masiis.shop.admin.service.product.PfUserSkuStockService;
import com.masiis.shop.admin.utils.WxPFNoticeUtils;
import com.masiis.shop.common.enums.platform.OperationType;
import com.masiis.shop.common.enums.platform.SkuStockLogType;
import com.masiis.shop.common.enums.platform.UserSkuStockLogType;
import com.masiis.shop.common.util.MobileMessageUtil;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.platform.order.*;
import com.masiis.shop.dao.platform.product.ComAgentLevelMapper;
import com.masiis.shop.dao.platform.product.ComSkuMapper;
import com.masiis.shop.dao.platform.product.ComSpuMapper;
import com.masiis.shop.dao.platform.product.PfSkuAgentMapper;
import com.masiis.shop.dao.platform.system.PbOperationLogMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.po.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.util.*;

/**
 * Created by ZhaoLiang on 2016/3/2.
 */
@Service
@Transactional
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
    private PfSkuStockService pfSkuStockService;
    @Resource
    private PfUserSkuStockService pfUserSkuStockService;
    @Resource
    private BOrderOperationLogService bOrderOperationLogService;
    @Resource
    private ComAgentLevelMapper comAgentLevelMapper;
    @Resource
    private PbOperationLogMapper pbOperationLogMapper;
    @Resource
    private PfSkuAgentMapper pfSkuAgentMapper;
    @Resource
    private PfBorderRecommenRewardMapper pfBorderRecommenRewardMapper;


    public int updatePfBorder(PfBorder pfBorder){
        return pfBorderMapper.updateById(pfBorder);
    }

    /**
     * 根据订单号获取订单商品
     *
     * @author ZhaoLiang
     * @date 2016/3/9 11:45
     */
    public List<PfBorderItem> getPfBorderItemByOrderId(Long pfBorderId) {
        return pfBorderItemMapper.selectAllByOrderId(pfBorderId);
    }

    /**
     * 获取订单
     *
     * @author ZhaoLiang
     * @date 2016/3/9 11:07
     */
    public PfBorder getPfBorderById(Long id) {
        return pfBorderMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据条件查询记录
     *
     * @param pageNo
     * @param pageSize
     * @param conditionMap
     * @return
     */
    public Map<String, Object> listByCondition(Integer pageNo, Integer pageSize, String sortName, String sortOrder, Map<String, Object> conditionMap) {
        String sort = "create_time desc";
        if (sortName != null) sort = sortName + " " + sortOrder;
        PageHelper.startPage(pageNo, pageSize, sort);
        List<PfBorder> pfBorders = pfBorderMapper.selectByCondition(conditionMap);
        PageInfo<PfBorder> pageInfo = new PageInfo<>(pfBorders);

        List<Order> orders = new ArrayList<>();
        for (PfBorder pbo : pfBorders) {
            ComUser comUser = comUserMapper.selectByPrimaryKey(pbo.getUserId());
            ComUser pUser   = comUserMapper.selectByPrimaryKey(pbo.getUserPid());
            ComUser recommenUser = pfBorderRecommenRewardMapper.selectRecommenUser(pbo.getId());
            PfBorderConsignee pfBorderConsignee = pfBorderConsigneeMapper.selectByBorderId(pbo.getId());
            PfBorderPayment pfBorderPayment = new PfBorderPayment();
            pfBorderPayment.setPfBorderId(pbo.getId());
            pfBorderPayment.setIsEnabled(1);
            if(conditionMap.get("payTypeId") !=null){
                pfBorderPayment.setPayTypeId((Integer)conditionMap.get("payTypeId"));
            }
            List<PfBorderPayment> pfBorderPayments = pfBorderPaymentMapper.selectByCondition(pfBorderPayment);
            List<PfBorderItem> pfBorderItems = pfBorderItemMapper.selectAllByOrderId(pbo.getId());

            Order order = new Order();
            order.setPfBorder(pbo);
            order.setComUser(comUser);
            order.setpUser(pUser);
            order.setRecommenUser(recommenUser);
            order.setPfBorderConsignee(pfBorderConsignee);
            order.setPfBorderPayments(pfBorderPayments);
            order.setPfBorderItems(pfBorderItems);

            orders.add(order);
        }

        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("total", pageInfo.getTotal());
        pageMap.put("rows", orders);

        return pageMap;
    }

    /**
     * 合伙线下支付订单列表
     * @param pageNo
     * @param pageSize
     * @param sortName
     * @param sortOrder
     * @param conditionMap
     *
     * @return
     */
    public Map<String, Object> offlineList(Integer pageNo, Integer pageSize, String sortName, String sortOrder, Map<String, Object> conditionMap){
        String sort = "pb.create_time desc";
        if (sortName != null) sort = sortName + " " + sortOrder;
        PageHelper.startPage(pageNo, pageSize, sort);
        List<Map<String, Object>> bOrderMaps = pfBorderMapper.selectByOffline(conditionMap);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(bOrderMaps);

        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("total", pageInfo.getTotal());
        pageMap.put("rows", bOrderMaps);

        return pageMap;
    }

    /**
     * 获取订单明细
     *
     * @param id
     * @return
     */
    public Order find(Long id) {
        PfBorder pfBorder = pfBorderMapper.selectByPrimaryKey(id);
        ComUser comUser = comUserMapper.selectByPrimaryKey(pfBorder.getUserId());
        PfBorderConsignee pfBorderConsignee = pfBorderConsigneeMapper.selectByBorderId(id);
        List<PfBorderFreight> pfBorderFreights = pfBorderFreightMapper.selectByBorderId(id);
        List<PfBorderItem> pfBorderItems = pfBorderItemMapper.selectAllByOrderId(id);

        List<ProductInfo> productInfos = new ArrayList<>();
        for (PfBorderItem pfBorderItem : pfBorderItems) {
            ComSku comSku = comSkuMapper.selectById(pfBorderItem.getSkuId());
            ComSpu comSpu = comSpuMapper.selectById(pfBorderItem.getSpuId());
            ComAgentLevel comAgentLevel = comAgentLevelMapper.selectByPrimaryKey(pfBorderItem.getAgentLevelId());
            PfSkuAgent pfSkuAgent = pfSkuAgentMapper.selectBySkuIdAndLevelId(pfBorderItem.getSkuId(), pfBorderItem.getAgentLevelId());

            ProductInfo productInfo = new ProductInfo();
            productInfo.setComSku(comSku);
            productInfo.setComSpu(comSpu);
            productInfo.setComAgentLevel(comAgentLevel);
            productInfo.setPfSkuAgent(pfSkuAgent);

            productInfos.add(productInfo);
        }

        Order order = new Order();
        order.setPfBorder(pfBorder);
        order.setComUser(comUser);
        order.setPfBorderConsignee(pfBorderConsignee);
        order.setPfBorderFreights(pfBorderFreights);
        order.setPfBorderItems(pfBorderItems);
        order.setProductInfos(productInfos);

        if (pfBorder.getPayStatus().intValue() == 1) {
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
     *
     * @param pfBorderFreight
     */
    public void delivery(PfBorderFreight pfBorderFreight,PbUser pbUser) throws Exception {
        PfBorder pfBorder = pfBorderMapper.selectByPrimaryKey(pfBorderFreight.getPfBorderId());
        pfBorder.setOrderStatus(8);
        pfBorder.setShipStatus(5);
        pfBorder.setShipTime(new Date());

        pfBorderFreight.setCreateTime(new Date());

        pfBorderMapper.updateById(pfBorder);
        pfBorderFreightMapper.insert(pfBorderFreight);

        updateOrderStock(pfBorder);

        //添加订单日志
        bOrderOperationLogService.insertBOrderOperationLog(pfBorder, "订单完成");

        //通知客户 * @param params   (1,商品名称;2,代理等级;3,订单编号(不是id);4,快递公司;5,快递单号)
        PfBorderConsignee pfBorderConsignee = pfBorderConsigneeMapper.selectByBorderId(pfBorder.getId());
        ComUser comUser = comUserMapper.selectByPrimaryKey(pfBorderConsignee.getUserId());
        List<PfBorderItem> borderItems = pfBorderItemMapper.selectAllByOrderId(pfBorder.getId());
        String skuNames = "";
        String levelNames = "";
        for (PfBorderItem borderItem : borderItems) {
            skuNames += borderItem.getSkuName();
            levelNames += comAgentLevelMapper.selectByPrimaryKey(borderItem.getAgentLevelId()).getName();
        }
        MobileMessageUtil.getInitialization("B").goodsOrderShipRemind(pfBorderConsignee.getMobile(), pfBorder.getOrderCode(), pfBorderFreight.getShipManName(), pfBorderFreight.getFreight());
        WxPFNoticeUtils.getInstance().orderShippedNotice(comUser, new String[]{skuNames, levelNames, pfBorder.getOrderCode(), pfBorderFreight.getShipManName(), pfBorderFreight.getFreight()}, PropertiesUtils.getStringValue("web.domain.name.address") + "/borderManage/borderDetils.html?id=" + pfBorder.getId());

        PbOperationLog pbOperationLog = new PbOperationLog();
        pbOperationLog.setOperateIp(InetAddress.getLocalHost().getHostAddress());
        pbOperationLog.setCreateTime(new Date());
        pbOperationLog.setPbUserId(pbUser.getId());
        pbOperationLog.setPbUserName(pbUser.getUserName());
        pbOperationLog.setOperateType(OperationType.Update.getCode());
        pbOperationLog.setRemark("发货");
        pbOperationLog.setOperateContent(pfBorderFreight.toString());
        int updateByPrimaryKey = pbOperationLogMapper.insert(pbOperationLog);
        if(updateByPrimaryKey==0){
            throw new Exception("日志新建平台发货失败!");
        }
    }

    /**
     * 更新库存
     *
     * @param pfBorder
     */
    public void updateOrderStock(PfBorder pfBorder) throws Exception {
        List<PfBorderItem> pfBorderItems = pfBorderItemMapper.selectAllByOrderId(pfBorder.getId());
        if (pfBorder.getUserPid().intValue() == 0 && pfBorder.getOrderType().intValue() != 2) {
            for (PfBorderItem pfBorderItem : pfBorderItems) {
                PfSkuStock pfSkuStock = pfSkuStockService.selectBySkuId(pfBorderItem.getSkuId());
                if (pfSkuStock.getStock() - pfBorderItem.getQuantity() >= 0 && pfSkuStock.getFrozenStock() - pfBorderItem.getQuantity() >= 0) {
                    pfSkuStockService.updateSkuStockWithLog(pfBorderItem.getQuantity(), pfSkuStock, pfBorder.getId(), SkuStockLogType.downAgent);
                } else {
                    throw new Exception("库存异常!");
                }
            }
        } else {
            for (PfBorderItem pfBorderItem : pfBorderItems) {
                Long userPid = pfBorder.getOrderType().intValue() == 2 ? pfBorder.getUserId() : pfBorder.getUserPid();
                PfUserSkuStock pfUserSkuStock = pfUserSkuStockService.selectByUserIdAndSkuId(userPid, pfBorderItem.getSkuId());
                if (pfUserSkuStock.getStock() - pfBorderItem.getQuantity() >= 0 && pfUserSkuStock.getFrozenStock() - pfBorderItem.getQuantity() >= 0) {
                    pfUserSkuStockService.updateUserSkuStockWithLog(pfBorderItem.getQuantity(), pfUserSkuStock, pfBorder.getId(), UserSkuStockLogType.downAgent);
                } else {
                    throw new Exception("库存异常!");
                }
            }
        }
    }

    /**
     * 查找合伙订单
     * @param borderId
     * @return
     */
    public PfBorder findById(Long borderId){
        return pfBorderMapper.selectByPrimaryKey(borderId);
    }

    /**
     * 待发货合伙订单列表
     *
     * @param pageNumber
     * @param pageSize
     * @param conditionMap
     * @return
     */
    public Map<String, Object> listDeliveryByCondition(Integer pageNumber, Integer pageSize, String sortName, String sortOrder, Map<String, Object> conditionMap) {
        String sort = "bo.create_time desc";
        if (sortName != null) sort = sortName + " " + sortOrder;

        PageHelper.startPage(pageNumber, pageSize, sort);
        List<Map<String, Object>> orderMaps = pfBorderMapper.selectDeliveryByCondition(conditionMap);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(orderMaps);

        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("total", pageInfo.getTotal());
        pageMap.put("rows", orderMaps);

        return pageMap;
    }
}
