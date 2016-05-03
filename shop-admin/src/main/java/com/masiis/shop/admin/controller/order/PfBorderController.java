package com.masiis.shop.admin.controller.order;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.admin.beans.order.Order;
import com.masiis.shop.admin.service.order.BOrderPayService;
import com.masiis.shop.admin.service.order.BOrderPaymentService;
import com.masiis.shop.admin.service.order.BOrderService;
import com.masiis.shop.admin.service.order.OrderQueueDealService;
import com.masiis.shop.dao.po.PfBorder;
import com.masiis.shop.dao.po.PfBorderFreight;
import com.masiis.shop.dao.po.PfBorderPayment;
import org.apache.commons.lang.StringUtils;
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
 * Created by cai_tb on 16/3/12.
 */
@Controller
@RequestMapping("/order/border")
public class PfBorderController {

    private final static Log log = LogFactory.getLog(PfBorderController.class);

    @Resource
    private BOrderService bOrderService;
    @Resource
    private OrderQueueDealService orderQueueDealService;
    @Resource
    private BOrderPaymentService bOrderPaymentService;
    @Resource
    private BOrderPayService bOrderPayService;

    @RequestMapping("/list.shtml")
    public String list(){
        return "order/border/list";
    }

    @RequestMapping("/list.do")
    @ResponseBody
    public Object list(HttpServletRequest request, HttpServletResponse response,
                       Integer pageNumber,
                       Integer pageSize,
                       String sortName,
                       String sortOrder,
                       PfBorder pfBorder){

        try {
            Map<String, Object> pageMap = bOrderService.listByCondition(pageNumber, pageSize, sortName, sortOrder, pfBorder);

            return pageMap;
        } catch (Exception e) {
            log.error("查询合伙人列表失败![pfBorder="+pfBorder+"]");
            e.printStackTrace();
        }

        return null;
    }

    @RequestMapping("detail.shtml")
    public ModelAndView detail(HttpServletRequest request, HttpServletResponse response, Long borderId){
        try {
            ModelAndView mav = new ModelAndView("order/border/detail");

            Order order = bOrderService.find(borderId);

            mav.addObject("order", order);

            return mav;
        } catch (Exception e) {
            log.error("查看合伙人订单明细失败![borderId="+borderId+"]");
            e.printStackTrace();
        }

        return null;
    }

    @RequestMapping("/offline/list.shtml")
    public String offlineList(){
        return "order/border/offlinePayList";
    }

    /**
     * 合伙人线下支付订单
     * @param request
     * @param response
     * @param pageNumber
     * @param pageSize
     * @param sortOrder
     * @param pfBorder
     * @return
     */
    @RequestMapping("/offline/list.do")
    @ResponseBody
    public Object offlineList(HttpServletRequest request, HttpServletResponse response,
                       Integer pageNumber,
                       Integer pageSize,
                       String sortName,
                       String sortOrder,
                       PfBorder pfBorder){

        try {
            pfBorder.setOrderStatus(9);
            Map<String, Object> pageMap = bOrderService.listByCondition(pageNumber, pageSize, sortName, sortOrder, pfBorder);

            return pageMap;
        } catch (Exception e) {
            log.error("查询合伙人线下支付订单列表失败![pfBorder="+pfBorder+"]");
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 合伙人线下支付订单收款确认
     * @param request
     * @param response
     * @param bOrderId  合伙人订单ID
     * @param outOrderId  银行流水号
     * @return
     */
    @RequestMapping("/offline/Receipt.do")
    @ResponseBody
    public Object Receipt(HttpServletRequest request, HttpServletResponse response, Long bOrderId, String outOrderId){

        try {
            PfBorderPayment borderPayment = bOrderPaymentService.findOfflinePayByBOrderId(bOrderId);
            bOrderPayService.mainPayBOrder(borderPayment, outOrderId, request.getServletContext().getRealPath("/"));

            return "success";
        } catch (Exception e) {
            log.error("合伙人线下支付收款确认失败![bOrderId="+bOrderId+"][outOrderId="+outOrderId+"]");
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 处理排单
     * @param request
     * @param response
     * @param borderId  订单id
     * @param sendType  发货类型
     * @return
     */
    @RequestMapping("/scheduling.do")
    @ResponseBody
    public Object scheduling(HttpServletRequest request, HttpServletResponse response, Long borderId, String sendType){

        Map<Long, String> orderMap = new HashMap<>();
        try {
            if(borderId == null || sendType == null){
                throw new RuntimeException("参数有误!不知道处理哪个订单![orderMap="+orderMap+"]");
            }
            orderMap.put(borderId, sendType);

            orderQueueDealService.commonQueuingOrder(orderMap);

            return "success";
        } catch (Exception e) {
            log.error("处理排单出错了![orderMap="+orderMap+"]");
            e.printStackTrace();
        }

        return "error";
    }

    /**
     * 发货
     * @param request
     * @param response
     * @param pfBorderFreight
     * @return
     */
    @RequestMapping("/delivery.do")
    @ResponseBody
    public Object delivery(HttpServletRequest request, HttpServletResponse response,
                           PfBorderFreight pfBorderFreight){

        try {
            if (pfBorderFreight.getShipManId() == null){
                return "请选择一个快递";
            }
            if(StringUtils.isBlank(pfBorderFreight.getFreight())){
                return "请填写运单号";
            }

            bOrderService.delivery(pfBorderFreight);

            return "success";
        } catch (Exception e) {
            log.error("合伙人订单发货失败![pfBorderFreight="+pfBorderFreight+"]");
            e.printStackTrace();
        }

        return "error";
    }

}
