package com.masiis.shop.web.platform.controller.shop;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.mall.order.SfOrderPaymentMapper;
import com.masiis.shop.dao.mallBeans.OrderMallDetail;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.common.constant.platform.SysConstants;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.order.ComShipManService;
import com.masiis.shop.web.mall.service.order.SfOrderService;
import com.masiis.shop.web.platform.service.shop.SfOrderShopService;
import com.masiis.shop.web.platform.service.system.ComDictionaryService;
import com.masiis.shop.web.common.service.UserService;
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
    private UserService userService;
    @Resource
    private ComShipManService comShipManService;


    @RequestMapping("/deliverOrder.do")
    @ResponseBody
    public String deliverOrder(HttpServletRequest request,
                          @RequestParam(required = true) String shipManName,
                          @RequestParam(required = true) Long orderId,
                          @RequestParam(required = true) String freight,
                          @RequestParam(required = true) String shipManId) throws Exception {
        logger.info("deliverOrder.do");
        JSONObject json = new JSONObject();
        try {
            ComUser user = getComUser(request);
            if (user == null) {
                throw new BusinessException("user不能为空");
            }
            sfOrderService.deliver(shipManName,orderId,freight,shipManId,user);
        } catch (Exception ex) {
            if (StringUtils.isNotBlank(ex.getMessage())) {
                throw new BusinessException(ex.getMessage(), ex);
            } else {
                throw new BusinessException("网络错误", ex);
            }
        }
        logger.info(json.toString());
        return json.toString();
    }

    /**
     * 订单详情
     * @author muchaofeng
     * @date 2016/3/16 15:00
     */
    @RequestMapping("/sfOrderDetal.html")
    public ModelAndView sfOrderDetal(HttpServletRequest request, Long id) throws Exception {
        OrderMallDetail orderMallDetail = new OrderMallDetail();
        ComUser user = getComUser(request);
        if (user == null) {
            throw new BusinessException("user不能为空");
        }
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
            stringBuffer.append("<p>承运公司：<span></span></p>");
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
        ModelAndView modelAndView = new ModelAndView();
        List<ComShipMan> comShipMans = comShipManService.list();
        modelAndView.addObject("comShipMans", comShipMans);
        modelAndView.addObject("stringBuffer", stringBuffer.toString());
        modelAndView.addObject("orderMallDetail", orderMallDetail);
        modelAndView.setViewName("platform/shop/shopxiangqing");
        return modelAndView;
    }

    /**
     * 分段查询小铺订单
     * @author muchaofeng
     * @date 2016/4/13 10:44
     */
    @RequestMapping("/stockShipOrder")
    public ModelAndView stockShipOrder(HttpServletRequest request, Integer orderStatus, Long shopId) throws Exception {
        ComUser comUser = getComUser(request);
        if (comUser == null){
            throw new BusinessException("user不能为空");
        }
        Integer sendType=null;
//        if(orderStatus==7){
//            sendType=1;
//        }
        List<SfOrder> sfOrders = sfOrderService.findOrdersByShopUserId(comUser.getId(), orderStatus, shopId,sendType);
        String index=null;
        if(orderStatus==null){
            index="0";//全部
        }else if (orderStatus == 0) {
            index="1";//待付款
        }else if (orderStatus == 3) {
            index="4";//已完成
        } else if (orderStatus == 7) {
            index="2";//代发货
        } else if (orderStatus == 8){
            index="3";//待收货
        }
        ModelAndView modelAndView = new ModelAndView();
        List<ComShipMan> comShipMans = comShipManService.list();
        modelAndView.addObject("comShipMans", comShipMans);
        modelAndView.addObject("index",index);
        modelAndView.addObject("sfOrders", sfOrders);
        modelAndView.addObject("sfOrderSize", sfOrders.size());
        modelAndView.setViewName("platform/shop/dingdanguanli");
        return modelAndView;
    }
    /**
     * 异步查询订单
     * @author muchaofeng
     * @date 2016/4/13 16:30
     */

    @RequestMapping("/clickSfOrder.do")
    @ResponseBody
    public List<SfOrder> clickSfOrderType(HttpServletRequest request, @RequestParam(required = true) Integer index,Integer sendType) {
        List<SfOrder> sfOrders=null;
        try {
            ComUser user = getComUser(request);
            if (user == null) {
                throw new BusinessException("user不能为空");
            }
            Long shopId =(Long) request.getSession().getAttribute("shopId");
            List<ComShipMan> comShipMans = comShipManService.list();
            request.getSession().setAttribute("comShipMans", comShipMans);
            if(index==0){
                sfOrders = sfOrderService.findOrdersByShopUserId(user.getId(), null, shopId,null);
            }else if(index==1){
                sfOrders = sfOrderService.findOrdersByShopUserId(user.getId(), 0, shopId,null);
            }else if(index==2){
                sfOrders = sfOrderService.findOrdersByShopUserId(user.getId(), 7, shopId, sendType);
            }else if(index==3){
                sfOrders = sfOrderService.findOrdersByShopUserId(user.getId(), 8, shopId,null);
            }else if(index==4){
                sfOrders = sfOrderService.findOrdersByShopUserId(user.getId(), 3, shopId,null);
            }else if(index==5){
                sfOrders = sfOrderService.findOrdersByShopUserId(user.getId(), 2, shopId,null);
            }
        } catch (Exception ex) {
            if (StringUtils.isNotBlank(ex.getMessage())) {
                throw new BusinessException(ex.getMessage(), ex);
            } else {
                throw new BusinessException("网络错误", ex);
            }
        }
        return sfOrders;
    }

}
