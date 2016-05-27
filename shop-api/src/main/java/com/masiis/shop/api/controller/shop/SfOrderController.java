package com.masiis.shop.api.controller.shop;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.api.bean.order.*;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.constants.SysConstants;
import com.masiis.shop.api.constants.SysResCodeCons;
import com.masiis.shop.api.controller.base.BaseController;
import com.masiis.shop.api.service.shop.SfOrderService;
import com.masiis.shop.api.service.shop.SfOrderShopService;
import com.masiis.shop.api.service.system.ComDictionaryService;
import com.masiis.shop.api.service.user.ComShipManService;
import com.masiis.shop.api.service.user.ComUserService;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.mall.order.SfOrderPaymentMapper;
import com.masiis.shop.dao.mallBeans.OrderMallDetail;
import com.masiis.shop.dao.po.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 小铺订单
 * @author muchaofeng
 * @date 2016/4/13 11:18
 */

@Controller
@RequestMapping("/sfOrderController")
public class SfOrderController extends BaseController {

    private static final Logger logger = Logger.getLogger(SfOrderController.class);
    @Resource
    private SfOrderService sfOrderService;
    @Resource
    private SfOrderShopService sfOrderShopService;
    @Resource
    private SfOrderPaymentMapper sfOrderPaymentMapper;
    @Resource
    private ComDictionaryService comDictionaryService;
    @Resource
    private ComUserService userService;
    @Resource
    private ComShipManService comShipManService;


    /**
     * 店铺订单 发货
     * @author muchaofeng
     * @date 2016/5/26 13:58
     */
    @RequestMapping("/deliverSfOrder.do")
    @ResponseBody
    @SignValid(paramType = BorderDeliverReq.class)
    public OrderListPagingRes deliverSfOrder(HttpServletRequest request, BorderDeliverReq req,ComUser user) throws Exception {
        logger.info("deliverSfOrder.do");
        OrderListPagingRes res = new OrderListPagingRes();
        try {
            String freight = req.getFreight();
            Long orderId = req.getOrderId();
            String shipManId = req.getShipManId();
            String shipManName = req.getShipManName();
            sfOrderService.deliver(shipManName,orderId,freight,shipManId,user);
            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        } catch (Exception ex) {
            if (StringUtils.isNotBlank(ex.getMessage())){
                res.setResMsg(ex.getMessage());
            } else {
                res.setResMsg("店铺订单 发货");
            }
        }
        logger.info(res);
        return res;
    }

    /**
     * 店铺 订单详情
     * @author muchaofeng
     * @date 2016/5/26 14:05
     */
    @RequestMapping("/sfOrderDetal.html")
    @ResponseBody
    @SignValid(paramType = BorderDetailReq.class)
    public BorderDetailRes sfOrderDetal(HttpServletRequest request,BorderDetailReq req,ComUser user) throws Exception {
        OrderMallDetail orderMallDetail = new OrderMallDetail();
        BorderDetailRes res = new BorderDetailRes();
        Long id = req.getId();
        try{
            SfOrder order = sfOrderService.findSforderByorderId(id);
            ComUser Buser = userService.getUserById(order.getUserId());
            String skuValue = PropertiesUtils.getStringValue(SysConstants.INDEX_PRODUCT_IMAGE_MIN);

            List<SfOrderItem> sfOrderItems = sfOrderShopService.findSfOrderItemBySfOrderId(id);
            StringBuffer stringBuffer =new StringBuffer();
            for (SfOrderItem sfOrderItem : sfOrderItems) {
                sfOrderItem.setSkuUrl(skuValue + sfOrderShopService.findComSkuImage(sfOrderItem.getSkuId()).getImgUrl());
                order.setTotalQuantity(order.getTotalQuantity() + sfOrderItem.getQuantity());//订单商品总量
            }
            //快递公司信息
            List<SfOrderFreight> sfOrderFreights = sfOrderShopService.findSfOrderFreight(id);
            if(sfOrderFreights!=null && sfOrderFreights.size()!=0){
                for (SfOrderFreight sfOrderFreight:sfOrderFreights) {
                    stringBuffer.append("<p>承运公司：<span>"+sfOrderFreight.getShipManName()+"</span></p>");
                    stringBuffer.append("<p>运单编号：<span>"+sfOrderFreight.getFreight()+"</span></p>");
                }
            }else {
                stringBuffer.append("<p>承运公司：<span></span></p> ");
                stringBuffer.append("<p>运单编号：<span></span></p>");
            }
            ComDictionary comDictionary = comDictionaryService.findComDictionary(order.getOrderStatus());
            order.setOrderSkuStatus(comDictionary.getValue());
            //支付方式
            List<SfOrderPayment> sfOrderPayments = sfOrderPaymentMapper.selectBySfOrderId(id);
            //收货人
            SfOrderConsignee sfOrderConsignee = sfOrderShopService.findSfOrderConsignee(id);
            orderMallDetail.setBuyerName(Buser.getWxNkName());
            orderMallDetail.setSfOrder(order);
            orderMallDetail.setSfOrderPayments(sfOrderPayments);
            orderMallDetail.setSfOrderItems(sfOrderItems);
            orderMallDetail.setSfOrderFreights(sfOrderFreights);
            orderMallDetail.setSfOrderConsignee(sfOrderConsignee);
            List<ComShipMan> comShipMans = comShipManService.list();

            res.setStringBuffer(stringBuffer.toString());
            res.setComShipMans(comShipMans);
            res.setOrderMallDetail(orderMallDetail);
            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        }catch (Exception ex){
            if (StringUtils.isNotBlank(ex.getMessage())) {
                res.setResMsg(ex.getMessage());
            } else {
                res.setResMsg("店铺 订单详情");
            }
        }
        return res;
    }

    /**
     * 分段查询小铺订单
     * @author muchaofeng
     * @date 2016/5/26 14:14
     */
    @RequestMapping("/stockShipOrder")
    @ResponseBody
    @SignValid(paramType = OrderListPagingReq.class)
    public OrderListPagingRes stockShipOrder(HttpServletRequest request, OrderListPagingReq req,ComUser comUser) throws Exception {
        OrderListPagingRes res = new OrderListPagingRes();
        try{
            Integer orderStatus = req.getOrderStatus();
            Long shopId = req.getShopId();
            List<SfOrder> sfOrders = sfOrderService.findOrdersByShopUserId(comUser.getId(), orderStatus, shopId);
            String index=null;
            if(orderStatus==null){
                index="0";//全部
            }else if (orderStatus == 0) {
                index="1";//待付款
            }else if (orderStatus == 3) {
                index="4";//已完成
            } else if (orderStatus == 8){
                index="3";//待收货
            }else if (orderStatus == 7) {
                index="2";//代发货
            }
            List<ComShipMan> comShipMans = comShipManService.list();
            res.setComShipMans(comShipMans);
            res.setSfOrders(sfOrders);
            res.setSfOrderSize(sfOrders.size());
            res.setIndex(index);
            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        }catch(Exception ex){
            if (StringUtils.isNotBlank(ex.getMessage())) {
                res.setResMsg(ex.getMessage());
            } else {
                res.setResMsg("店铺分页查询出货订单");
            }
        }
        return res;
    }
    /**
     * 异步查询订单
     * @author muchaofeng
     * @date 2016/4/13 16:30
     */

    @RequestMapping("/clickSfOrder.do")
    @ResponseBody
    @SignValid(paramType = OrderListPagingReq.class)
    public OrderListPagingRes clickSfOrderType(HttpServletRequest request,OrderListPagingReq req,ComUser user) {
        List<SfOrder> sfOrders=null;
        OrderListPagingRes res = new OrderListPagingRes();
        try {
            Integer index = req.getIndex();
            Long shopId =(Long) request.getSession().getAttribute("shopId");
            List<ComShipMan> comShipMans = comShipManService.list();
//            request.getSession().setAttribute("comShipMans", comShipMans);
            if(index==0){
                sfOrders = sfOrderService.findOrdersByShopUserId(user.getId(), null, shopId);
            }else if(index==1){
                sfOrders = sfOrderService.findOrdersByShopUserId(user.getId(), 0, shopId);
            }else if(index==2){
                sfOrders = sfOrderService.findOrdersByShopUserId(user.getId(), 7, shopId);
            }else if(index==4){
                sfOrders = sfOrderService.findOrdersByShopUserId(user.getId(), 3, shopId);
            }else if(index==3){
                sfOrders = sfOrderService.findOrdersByShopUserId(user.getId(), 8, shopId);
            }
            res.setSfOrders(sfOrders);
            res.setComShipMans(comShipMans);
            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        } catch (Exception ex) {
            if (StringUtils.isNotBlank(ex.getMessage())) {
                res.setResMsg(ex.getMessage());
            } else {
                res.setResMsg("店铺 异步查询出货订单");
            }
        }
        return res;
    }

}
