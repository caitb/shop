package com.masiis.shop.admin.controller.order;

import com.masiis.shop.admin.beans.order.Order;
import com.masiis.shop.admin.controller.base.BaseController;
import com.masiis.shop.admin.service.order.OrderService;
import com.masiis.shop.dao.po.SfOrderFreight;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 店铺订单
 * Created by cai_tb on 16/4/14.
 */
@Controller
@RequestMapping("/order/order")
public class SfOrderController extends BaseController {

    private final static Log log = LogFactory.getLog(SfOrderController.class);

    @Resource
    private OrderService orderService;

    @RequestMapping("/list.shtml")
    public String list(){
        return "order/order/list";
    }

    @RequestMapping("/list.do")
    @ResponseBody
    public Object list(HttpServletRequest request, HttpServletResponse response,
                       Integer pageNumber,
                       Integer pageSize,
                       String sortOrder){

        Map<String, Object> conditionMap = new HashMap<>();
        try {

            Map<String, Object> pageMap = orderService.listByCondition(pageNumber, pageSize, conditionMap);
            return pageMap;
        } catch (Exception e) {
            log.error("获取店铺订单列表失败![conditionMap="+conditionMap+"]");
            e.printStackTrace();
        }

        return "error";
    }

    /**
     * 订单详情
     * @param request
     * @param response
     * @param orderId
     * @return
     */
    @RequestMapping("/detail.shtml")
    public ModelAndView detail(HttpServletRequest request, HttpServletResponse response, Long orderId){
        try {
            ModelAndView mav = new ModelAndView("order/order/detail");

            Order order = orderService.find(orderId);

            mav.addObject("order", order);

            return mav;
        } catch (Exception e) {
            log.error("查看合伙人订单明细失败![orderId="+orderId+"]");
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 平台代理小铺发货
     * @param request
     * @param response
     * @param sfOrderFreight
     * @return
     */
    @RequestMapping("/delivery.do")
    @ResponseBody
    public Object delivery(HttpServletRequest request, HttpServletResponse response,
                           SfOrderFreight sfOrderFreight){

        try {
            if (sfOrderFreight.getShipManId() == null){
                return "请选择一个快递";
            }
            if(StringUtils.isBlank(sfOrderFreight.getFreight())){
                return "请填写运单号";
            }

            orderService.delivery(sfOrderFreight);

            return "success";
        } catch (Exception e) {
            log.error("合伙人订单发货失败![sfOrderFreight="+sfOrderFreight+"]");
            e.printStackTrace();
        }

        return null;
    }
}
