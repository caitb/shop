package com.masiis.shop.web.mall.controller.order;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.PropertiesUtils;
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
import java.util.Iterator;
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
}
