package com.masiis.shop.admin.controller.order;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.admin.beans.order.Order;
import com.masiis.shop.admin.controller.base.BaseController;
import com.masiis.shop.admin.service.order.*;
import com.masiis.shop.admin.service.product.PfUserSkuStockService;
import com.masiis.shop.admin.service.system.DictionaryService;
import com.masiis.shop.common.enums.platform.BOrderShipStatus;
import com.masiis.shop.common.enums.platform.BOrderStatus;
import com.masiis.shop.common.enums.platform.BOrderType;
import com.masiis.shop.dao.po.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cai_tb on 16/3/12.
 */
@Controller
@RequestMapping("/order/border")
public class PfBorderController extends BaseController {

    private final static Log log = LogFactory.getLog(PfBorderController.class);

    @Resource
    private BOrderService bOrderService;
    @Resource
    private OrderQueueDealService orderQueueDealService;
    @Resource
    private BOrderPaymentService bOrderPaymentService;
    @Resource
    private BOrderPayService bOrderPayService;
    @Resource
    private DictionaryService dictionaryService;
    @Resource
    private BorderItemService borderItemService;
    @Resource
    private PfUserSkuStockService userSkuStockService;

    @RequestMapping("/list.shtml")
    public String list(Model model) {
        try {
            List<ComDictionary> payTypeList = dictionaryService.pickListOfBaseData("COM_USER_PAY_TYPE");//支付方式
            model.addAttribute("payTypes", payTypeList);
            model.addAttribute("bOrderTypes", BOrderType.values());
            model.addAttribute("bOrderStatuses", BOrderStatus.values());
            model.addAttribute("bOrderShipStatuses", BOrderShipStatus.values());
        } catch (Exception e) {
            log.error("获取支付方式失败!"+e);
            e.printStackTrace();
        }

        return "order/border/list";
    }

    @RequestMapping("/list.do")
    @ResponseBody
    public Object list(HttpServletRequest request, HttpServletResponse response,
                       Integer pageNumber,
                       Integer pageSize,
                       String sortName,
                       String sortOrder,
                       String orderCode,
                       String beginTime,
                       String endTime,
                       Integer orderType,
                       Integer orderStatus,
                       Integer payStatus,
                       Integer shipStatus,
                       Integer isCounting,
                       Integer payTypeId
    ) {

        Map<String, Object> conditionMap = new HashMap<>();
        try {
            if(StringUtils.isNotBlank(orderCode)){
                conditionMap.put("orderCode", orderCode);
            }
            if(StringUtils.isNotBlank(request.getParameter("uRealName"))){
                conditionMap.put("uRealName", "%"+request.getParameter("uRealName")+"%");
            }
            if(StringUtils.isNotBlank(request.getParameter("skuName"))){
                conditionMap.put("skuName", "%"+request.getParameter("skuName")+"%");
            }
            if(orderType != null){
                conditionMap.put("orderType", orderType);
            }
            if(orderStatus != null){
                conditionMap.put("orderStatus", orderStatus);
            }
            if(payStatus != null){
                conditionMap.put("payStatus", payStatus);
            }
            if(shipStatus != null){
                conditionMap.put("shipStatus", shipStatus);
            }
            if(isCounting != null){
                conditionMap.put("isCounting", isCounting);
            }
            if(payTypeId != null){
                conditionMap.put("payTypeId", payTypeId);
            }
            Map<String, Object> pageMap = bOrderService.listByCondition(pageNumber, pageSize, sortName, sortOrder, conditionMap);
            return pageMap;
        } catch (Exception e) {
            log.error("查询合伙人列表失败![conditionMap=" + conditionMap + "]"+e);
            e.printStackTrace();
        }

        return null;
    }

    @RequestMapping("detail.shtml")
    public ModelAndView detail(HttpServletRequest request, HttpServletResponse response, Long borderId) {
        try {
            ModelAndView mav = new ModelAndView("order/border/detail");

            Order order = bOrderService.find(borderId);

            mav.addObject("order", order);

            return mav;
        } catch (Exception e) {
            log.error("查看合伙人订单明细失败![borderId=" + borderId + "]"+e);
            e.printStackTrace();
        }

        return null;
    }

    @RequestMapping("/offline/list.shtml")
    public String offlineList(Model model) {
        model.addAttribute("bOrderTypes", BOrderType.values());
        return "order/border/offlinePayList";
    }

    /**
     * 合伙人线下支付订单
     *
     * @param request
     * @param response
     * @param pageNumber
     * @param pageSize
     * @param sortOrder
     * @return
     */
    @RequestMapping("/offline/list.do")
    @ResponseBody
    public Object offlineList(HttpServletRequest request, HttpServletResponse response,
                              Integer pageNumber,
                              Integer pageSize,
                              String sortName,
                              String sortOrder,
                              Integer orderStatus) {

        Map<String, Object> conditionMap = new HashMap<>();
        try {
            if(StringUtils.isNotBlank(request.getParameter("orderCode"))){
                conditionMap.put("orderCode", request.getParameter("orderCode"));
            }
            if(StringUtils.isNotBlank(request.getParameter("realName"))){
                conditionMap.put("realName", "%"+request.getParameter("realName")+"%");
            }
            if(StringUtils.isNotBlank(request.getParameter("orderType"))){
                conditionMap.put("orderType", request.getParameter("orderType"));
            }
            if(StringUtils.isNotBlank(request.getParameter("startTime"))){
                conditionMap.put("startTime", request.getParameter("startTime"));
            }
            if(StringUtils.isNotBlank(request.getParameter("endTime"))){
                conditionMap.put("endTime", request.getParameter("endTime"));
            }

            Map<String, Object> pageMap = bOrderService.offlineList(pageNumber, pageSize, sortName, sortOrder, conditionMap);
            return pageMap;
        } catch (Exception e) {
            log.error("查询合伙人线下支付订单列表失败![conditionMap=" + conditionMap + "]"+e);
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 合伙人线下支付订单收款确认
     *
     * @param request
     * @param response
     * @param bOrderId   合伙人订单ID
     * @param outOrderId 银行流水号
     * @param payAmount  实付金额
     * @return
     */
    @RequestMapping("/offline/Receipt.do")
    @ResponseBody
    public Object Receipt(HttpServletRequest request, HttpServletResponse response, Long bOrderId, String outOrderId, BigDecimal payAmount) {
        Map<String, String> resultMap = new HashMap<>();

        try {
            PfBorder pfBorder = bOrderService.findById(bOrderId);
            if (pfBorder == null) {
                resultMap.put("result_code", "1");
                resultMap.put("result_msg", "此订单不存在!");
                return resultMap;
            }
//            if (payAmount.compareTo(pfBorder.getReceivableAmount())==-1 && pfBorder.getOrderType().intValue() != 0) {
//                resultMap.put("result_code", "1");
//                resultMap.put("result_msg", "此订单不是代理订单,必须全额支付!");
//                return resultMap;
//            }

            PfBorderPayment borderPayment = bOrderPaymentService.findOfflinePayByBOrderId(bOrderId);
            bOrderPayService.payBOrderOffline(borderPayment, outOrderId, payAmount, request.getServletContext().getRealPath("/"), getPbUser(request));
            resultMap.put("result_code", "0");
            resultMap.put("result_msg", "确认收款成功!");

            return resultMap;
        } catch (Exception e) {
            log.error("合伙人线下支付收款确认失败![bOrderId=" + bOrderId + "][outOrderId=" + outOrderId + "]"+e);
            e.printStackTrace();

            resultMap.put("result_code", "1");
            resultMap.put("result_msg", "操作异常!");
            return resultMap;
        }

    }

    /**
     * 处理排单
     *
     * @param request
     * @param response
     * @param borderId 订单id
     * @param sendType 发货类型
     * @return
     */
    @RequestMapping("/scheduling.do")
    @ResponseBody
    public Object scheduling(HttpServletRequest request, HttpServletResponse response, Long borderId, String sendType) {

        Map<Long, String> orderMap = new HashMap<>();
        try {
            if (borderId == null || sendType == null) {
                throw new RuntimeException("参数有误!不知道处理哪个订单![orderMap=" + orderMap + "]");
            }
            orderMap.put(borderId, sendType);

            orderQueueDealService.commonQueuingOrder(orderMap);

            return "success";
        } catch (Exception e) {
            log.error("处理排单出错了![orderMap=" + orderMap + "]"+e);
            e.printStackTrace();
        }

        return "error";
    }

    /**
     * 发货
     *
     * @param request
     * @param response
     * @param pfBorderFreight
     * @return
     */
    @RequestMapping("/delivery.do")
    @ResponseBody
    public Object delivery(HttpServletRequest request, HttpServletResponse response,
                           PfBorderFreight pfBorderFreight) {

        try {
            if (pfBorderFreight.getShipManId() == null) {
                return "请选择一个快递";
            }
            if (StringUtils.isBlank(pfBorderFreight.getFreight())) {
                return "请填写运单号";
            }

            PfBorder pfBorder = bOrderService.findById(pfBorderFreight.getPfBorderId());
            if(pfBorder == null){
                return "此订单不存在!";
            }

            PfBorderItem pfBorderItem = borderItemService.findByBorderId(pfBorder.getId());
            PfUserSkuStock pfUserSkuStock = userSkuStockService.findByUserIdAndSkuId(pfBorder.getUserId(), pfBorderItem.getSkuId());
            if(pfUserSkuStock.getStock()-pfBorderItem.getQuantity()<0){
                return "库存不足,请补货!";
            }

            bOrderService.delivery(pfBorderFreight, getPbUser(request));

            return "success";
        } catch (Exception e) {
            log.error("合伙人订单发货失败![pfBorderFreight=" + pfBorderFreight + "]"+e);
            e.printStackTrace();
        }

        return "error";
    }

}
