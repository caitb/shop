package com.masiis.shop.web.mall.controller.order;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.order.BorderDetail;
import com.masiis.shop.dao.mallBeans.OrderMallDetail;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.mall.constants.SysConstants;
import com.masiis.shop.web.mall.controller.base.BaseController;
import com.masiis.shop.web.mall.service.order.SfOrderManageService;
import com.masiis.shop.web.mall.service.user.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单
 * @author muchaofeng
 * @date 2016/4/8 17:56
 */
@Controller
@RequestMapping("/sfOrderManagerController")
public class SfOrderManagerController extends BaseController {

    @Resource
    private SfOrderManageService sfOrderManageService;
    @Resource
    private UserService userService;


    /**
     * 确认收货
     * @author muchaofeng
     * @date 2016/4/9 15:28
     */
    @RequestMapping("/deliverSfOrder.do")
    @ResponseBody
    public String deliverSfOrder(@RequestParam(required = true) Long orderId) {
        JSONObject json = new JSONObject();
        try {
            sfOrderManageService.deliver(orderId);
        } catch (Exception ex) {
            if (StringUtils.isNotBlank(ex.getMessage())) {
                throw new BusinessException(ex.getMessage(), ex);
            } else {
                throw new BusinessException("网络错误", ex);
            }
        }
        return json.toString();
    }

    /**
     * 订单详情
     * <p/>
     * 进货订单详情
     *
     * @author muchaofeng
     * @date 2016/3/16 15:00
     */
    @RequestMapping("/borderDetils.html")
    public ModelAndView borderDetils(HttpServletRequest request, Long id) throws Exception {
        OrderMallDetail orderMallDetail = new OrderMallDetail();
        ComUser user = getComUser(request);
        String skuValue = PropertiesUtils.getStringValue(SysConstants.INDEX_PRODUCT_IMAGE_MIN);
        SfOrder order = sfOrderManageService.findOrderByOrderId(id);
        List<SfOrderItem> sfOrderItems = sfOrderManageService.findSfOrderItemBySfOrderId(id);
        StringBuffer stringBuffer =new StringBuffer();
        for (SfOrderItem sfOrderItem : sfOrderItems) {
            sfOrderItem.setSkuUrl(skuValue + sfOrderManageService.findComSkuImage(sfOrderItem.getSkuId()).getImgUrl());
            order.setTotalQuantity(order.getTotalQuantity() + sfOrderItem.getQuantity());//订单商品总量
        }
        //快递公司信息
        List<SfOrderFreight> sfOrderFreights = sfOrderManageService.findSfOrderFreight(id);
        if(sfOrderFreights.size()!=0 && sfOrderFreights!=null){
            for (SfOrderFreight sfOrderFreight:sfOrderFreights) {
                stringBuffer.append("<p>承运公司：<span>"+sfOrderFreight.getShipManName()+"</span></p>");
                stringBuffer.append("<p>运单编号：<span>"+sfOrderFreight.getFreight()+"</span></p>");
            }
        }else {
            stringBuffer.append("<p>承运公司：<span></span></p>");
            stringBuffer.append("<p>运单编号：<span></span></p>");
        }

        //收货人
        SfOrderConsignee sfOrderConsignee = sfOrderManageService.findSfOrderConsignee(id);
        orderMallDetail.setSfOrder(order);
        orderMallDetail.setSfOrderItems(sfOrderItems);
        orderMallDetail.setSfOrderFreights(sfOrderFreights);
        orderMallDetail.setSfOrderConsignee(sfOrderConsignee);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("stringBuffer", stringBuffer.toString());
        modelAndView.addObject("orderMallDetail", orderMallDetail);
        modelAndView.setViewName("platform/order/dingdanxiangqing");
        return modelAndView;
    }

    /**
     * 分段查询进货订单
     * @author muchaofeng
     * @date 2016/4/8 18:00
     */
    @RequestMapping("/stockOrder")
    public ModelAndView stockOrder(HttpServletRequest request, Integer orderStatus, Integer sendType) throws Exception {
        ComUser comUser = getComUser(request);
        List<SfOrder> sfOrders = sfOrderManageService.findOrdersByUserId(comUser.getId(), orderStatus, sendType);
        String index=null;
        if(orderStatus==null && sendType==null){
            index="0";//全部
        }else if (orderStatus == 0) {
            index="1";//待付款
        }else if (orderStatus == 7) {
            index="2";//代发货
        } else if (orderStatus == 8 && sendType==2){
            index="3";//待收货
        } else if (orderStatus == 3) {
            index="4";//已完成
        }
        String skuValue = PropertiesUtils.getStringValue(SysConstants.INDEX_PRODUCT_IMAGE_MIN);
        if (sfOrders != null && sfOrders.size() != 0) {
            for (SfOrder sfOrder : sfOrders) {
                List<SfOrderItem> sfOrderItems = sfOrderManageService.findSfOrderItemBySfOrderId(sfOrder.getId());
                for (SfOrderItem sfOrderItem : sfOrderItems) {
                    sfOrderItem.setSkuUrl(skuValue + sfOrderManageService.findComSkuImage(sfOrderItem.getSkuId()).getImgUrl());
                    sfOrder.setTotalQuantity(sfOrder.getTotalQuantity() + sfOrderItem.getQuantity());//订单商品总量
                }
                sfOrder.setSfOrderItems(sfOrderItems);
            }
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("index",index);
        modelAndView.addObject("sfOrders", sfOrders);
        modelAndView.setViewName("mall/order/wodedingdan");
        return modelAndView;
    }
    /**
     * 异步查询进货订单
     * @author muchaofeng
     * @date 2016/4/9 10:44
     */
    @RequestMapping("/clickSfOrderType.do")
    @ResponseBody
    public List<SfOrder> clickSfOrderType(HttpServletRequest request, @RequestParam(required = true) Integer index) {
        List<SfOrder> sfOrders=null;
        try {
            ComUser user = getComUser(request);
            if (user == null) {
                user = userService.getUserById(1l);
                request.getSession().setAttribute("comUser", user);
            }
            if(index==0){
                sfOrders = sfOrderManageService.findOrdersByUserId(user.getId(), null, null);
            }else if(index==1){
                sfOrders = sfOrderManageService.findOrdersByUserId(user.getId(), 0, null);
            }else if(index==2){
                sfOrders = sfOrderManageService.findOrdersByUserId(user.getId(), 7, null);
            }else if(index==3){
                sfOrders = sfOrderManageService.findOrdersByUserId(user.getId(), 8, 2);
            }else if(index==4){
                sfOrders = sfOrderManageService.findOrdersByUserId(user.getId(), 3, null);
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

    /**
     * 订单管理
     * @author muchaofeng
     * @date 2016/4/2 14:09
     */
    @RequestMapping("/borderManagement.html")
    public ModelAndView borderManagement(HttpServletRequest request, Integer orderStatus, Integer shipStatus) throws Exception{
        ComUser user = getComUser(request);
        if (user == null) {
            user = userService.getUserById(1l);
        }
        SfUserRelation sfUserRelation = sfOrderManageService.findSfUserRelationByUserId(user.getId());
        ComUser userPid = userService.getUserById(sfUserRelation.getUserPid());
        List<SfOrder> sfOrders = sfOrderManageService.findOrdersByUserId(user.getId(), null, null);
        List<SfOrder> sfOrders0 = new ArrayList<>();
        List<SfOrder> sfOrders7 = new ArrayList<>();
        List<SfOrder> sfOrders8 = new ArrayList<>();
        for (SfOrder sfOrder : sfOrders) {
            if(sfOrder.getOrderStatus() == 0) {
                sfOrders0.add(sfOrder);//待付款
            }else if (sfOrder.getOrderStatus() == 7 ) {
                sfOrders7.add(sfOrder);//代发货
            }else if (sfOrder.getOrderStatus() == 8 ) {
                sfOrders8.add(sfOrder);//待收货
            }
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("sfOrders0", sfOrders0.size());
        modelAndView.addObject("sfOrders7", sfOrders7.size());
        modelAndView.addObject("sfOrders8", sfOrders8.size());
        modelAndView.addObject("user", user);
        modelAndView.addObject("userPid", userPid);
        modelAndView.setViewName("mall/order/gerenzhongxin");
        return modelAndView;
    }
}
